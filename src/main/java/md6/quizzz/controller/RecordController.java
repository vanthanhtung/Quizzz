package md6.quizzz.controller;

import lombok.RequiredArgsConstructor;
import md6.quizzz.dto.LoginRequest;
import md6.quizzz.dto.RecordRequest;
import md6.quizzz.model.*;
import md6.quizzz.model.Record;
import md6.quizzz.repository.RecordAnswerRepository;
import md6.quizzz.service.UserDetailsImpl;
import md6.quizzz.service.UserDetailsServiceImpl;
import md6.quizzz.service.appUserService.AppUserService;
import md6.quizzz.service.examService.ExamService;
import md6.quizzz.service.quizAnswerService.QuizAnswerService;
import md6.quizzz.service.quizService.QuizService;
import md6.quizzz.service.recordService.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/records")
public class RecordController {
    @Autowired
    RecordService recordService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    QuizAnswerService quizAnswerService;

    @Autowired
    QuizService quizService;

    @Autowired
    ExamService examService;

    @Autowired
    RecordAnswerRepository recordAnswerRepository;

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?> getExamResultOfUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            Optional<AppUser> curUser = appUserService.findByUsername(((UserDetails) principal).getUsername());
            Iterable<Record> allByUser = recordService.findAllByUser(curUser.get());
            return new ResponseEntity<>(allByUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @Transactional
    public ResponseEntity<Record> create(@RequestBody RecordRequest recordRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<AppUser> currUser = appUserService.findByUsername(userDetails.getUsername());
        Optional<Exam> currExam = examService.findById(recordRequest.getExam().getId());
        Record record = new Record();
        record.setExam(currExam.get());
        record.setRecordAnswers(new HashSet<>());
        recordRequest.getRecordAnswer().forEach(m -> {
            QuizAnswer quizAnswer = quizAnswerService.getById(m).get();
            RecordAnswer recordAnswer = new RecordAnswer();
            recordAnswer.set_correct(quizAnswer.is_correct());
            recordAnswer.setQuiz(quizAnswer.getQuiz());
            quizAnswer.getQuiz().getRecordAnswers().add(recordAnswer);
            record.getRecordAnswers().add(recordAnswer);
        });


        Set<RecordAnswer> recordAnswers = record.getRecordAnswers();
        double lengthExam = currExam.get().getQuizSet().size();
        double correctCount = currExam.get().getScore() / lengthExam;
        double recordPoint = 0;

        for (Quiz quiz : currExam.get().getQuizSet()) {
            boolean onlyCorrect = quiz.getType() != 2;
            if (onlyCorrect) {
                RecordAnswer recordAnswer = quiz.getRecordAnswers().stream().filter(m-> m.getId() == null).findFirst().get();
                if (!recordAnswer.is_correct()) continue;
                recordPoint += correctCount;
            } else {
                int correctOneCount = 0;
                long correctInOneQuiz = quiz.getAnswers().stream().filter(QuizAnswer::is_correct).count();
                if (quiz.getRecordAnswers().stream().allMatch(RecordAnswer::is_correct))
                    correctOneCount = quiz.getRecordAnswers().size();
                recordPoint += (double) correctOneCount / correctInOneQuiz * correctCount;
            }
        }

        record.setScore(recordPoint);
        record.setAppUser(currUser.get());
        recordService.save(record);
        Set<RecordAnswer> list = record.getRecordAnswers();
        recordAnswerRepository.saveAll(list);

        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> getAllDoneExam() {
        return new ResponseEntity<>(recordService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> getUserExamById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(recordService.getById(id), HttpStatus.OK);
    }
}
