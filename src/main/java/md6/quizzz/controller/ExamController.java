package md6.quizzz.controller;

import md6.quizzz.model.Exam;
import md6.quizzz.model.ExamRequest;
import md6.quizzz.service.examService.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/exams")
public class ExamController {
    @Autowired
    ExamService examService;


    @GetMapping()
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(examService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Exam> exam = examService.findById(id);
        if (!exam.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(exam, HttpStatus.OK);
    }

    @PostMapping()
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> save(@Validated @RequestBody Exam exam, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        examService.save(exam);
        return new ResponseEntity<>(exam, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> save(@Validated @RequestBody ExamRequest examRequest, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        examService.save(examRequest);
        return new ResponseEntity<>(examRequest, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> edit(@PathVariable Long id, @Validated @RequestBody Exam exam, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Optional<Exam> currentTest = examService.findById(id);
        if (currentTest.isPresent()) {
            currentTest.get().setEnabled(false);
            Exam examSaved = examService.save(exam);
            return new ResponseEntity<>(examSaved, HttpStatus.OK);
        }
        return new ResponseEntity<>(currentTest, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Exam> currentTest = examService.findById(id);
        currentTest.ifPresent(exam -> exam.setEnabled(false));
        return new ResponseEntity<>(currentTest, HttpStatus.OK);
    }
}
