package md6.quizzz.controller;

import lombok.RequiredArgsConstructor;
import md6.quizzz.model.*;
import md6.quizzz.model.Record;
import md6.quizzz.repository.RecordAnswerRepository;
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
    public ResponseEntity<Record> create(@RequestBody Record record) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<AppUser> currUser = appUserService.findByUsername(principal.getName());
        Optional<Exam> currExam = examService.findById(record.getExam().getId());
        Set<RecordAnswer> recordAnswers = record.getRecordAnswers();
        double lengthExam = currExam.get().getQuizSet().size();
        double correctCount = currExam.get().getScore() / lengthExam;
        double recordPoint = 0;

        for (Quiz quiz : currExam.get().getQuizSet()) {
            boolean onlyCorrect = quiz.getType() != 2;
            if (onlyCorrect && quiz.getRecordAnswers().get(0).is_correct()) {
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
        record.setAppUser(currUser.get());recordService.save(record);
        recordAnswerRepository.saveAll(record.getRecordAnswers());

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
