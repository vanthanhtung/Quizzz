package md6.quizzz.controller;

import lombok.RequiredArgsConstructor;
import md6.quizzz.dto.ExamRespon;
import md6.quizzz.dto.LoginRequest;
import md6.quizzz.dto.RecordRequest;
import md6.quizzz.dto.RecordRespon;
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


import java.util.*;

import java.sql.Timestamp;
import java.util.Date;
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
        record.setRecordAnswers(new ArrayList<>());
        recordRequest.getRecordAnswer().forEach(m -> {
            QuizAnswer quizAnswer = quizAnswerService.getById(m).get();
            RecordAnswer recordAnswer = new RecordAnswer();
            recordAnswer.set_correct(quizAnswer.is_correct());
            recordAnswer.setQuiz(quizAnswer.getQuiz());
            recordAnswer.setContent(quizAnswer.getContent());
            quizAnswer.getQuiz().getRecordAnswers().add(recordAnswer);
            record.getRecordAnswers().add(recordAnswer);
        });

        double lengthExam = currExam.get().getQuizSet().size();
        double correctCount = currExam.get().getScore() / lengthExam;
        double recordPoint = 0;

        for (Quiz quiz : currExam.get().getQuizSet()) {
            boolean onlyCorrect = quiz.getType() != 2;
            if (onlyCorrect) {
                RecordAnswer recordAnswer = quiz.getRecordAnswers().stream().filter(m -> m.getId() == null).findFirst().get();
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
        record.setStarted_at(currExam.get().getStarted_at());
        record.setFinished_at(new Date(System.currentTimeMillis()));
        recordService.save(record);
        List<RecordAnswer> list = record.getRecordAnswers();
        recordAnswerRepository.saveAll(list);

        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @PostMapping("/end/{id}")
    public ResponseEntity<?> endExam(@PathVariable Long id){
        Record record = recordService.getById(id).get();
        record.setFinished_at(new Date(System.currentTimeMillis()));
        recordService.save(record);
        return new ResponseEntity<>(record,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> getAllDoneExam() {
        return new ResponseEntity<>(recordService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public ResponseEntity<?> getUserExamById(@PathVariable("id") Long id) {
        Record record = recordService.getById(id).get();
        RecordRespon recordRespon = new RecordRespon();
        Exam exam = record.getExam();
        ExamRespon examRespon = new ExamRespon();
        examRespon.setDuration(exam.getDuration());
        examRespon.setEnabled(exam.getEnabled());
        examRespon.setExam_code(exam.getExam_code());
        examRespon.setExam_name(exam.getExam_name());
        examRespon.setId(exam.getId());
        examRespon.setScore(exam.getScore());
        examRespon.setStarted_at(exam.getStarted_at());

        Set<Quiz> quizSet = exam.getQuizSet();
        ArrayList<Quiz> quizList = new ArrayList<>();
        for(Quiz x: quizSet){
            quizList.add(x);
        }

        Collections.sort(quizList,Collections.reverseOrder());
        examRespon.setQuizSet(quizList);

        recordRespon.setAppUser(record.getAppUser());
        recordRespon.setFinished_at(record.getFinished_at());
        recordRespon.setId(record.getId());
        recordRespon.setScore(record.getScore());
        recordRespon.setRecordAnswers(record.getRecordAnswers());
        recordRespon.setExamRespon(examRespon);
        recordRespon.setStarted_at(record.getStarted_at());

        return new ResponseEntity<>(recordRespon, HttpStatus.OK);
    }
//
//    @GetMapping("/asd/{id}")
//    @Secured({"ROLE_USER"})
//    public ResponseEntity<?> getAllRecordAnswer(@RequestBody RecordRequest recordRequest, @PathVariable Long id) {
////        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////        Optional<AppUser> currUser = appUserService.findByUsername(userDetails.getUsername());
//        List<Long> list = new ArrayList<>();
//        Optional<Record> record = recordService.getById(id);
//
//        for(RecordAnswer recordAnswer : record.get().getRecordAnswers()){
//            list.add(recordAnswer.getId());
//        }
//        recordRequest.setRecordAnswer(list);
//
//
//        return new ResponseEntity<>(recordRequest, HttpStatus.OK);
//
//    }
}
