package md6.quizzz.controller;

import md6.quizzz.model.Exam;
import md6.quizzz.service.examService.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/exams")
public class ExamController {
    @Autowired
    ExamService examService;

    @GetMapping()
    public ResponseEntity<Iterable<Exam>> findAll(){
        return new ResponseEntity<>(examService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Exam>> findById(@PathVariable Long id){
        Optional<Exam> exam = examService.findById(id);
        if (!exam.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(exam, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Exam> save(@RequestBody Exam exam) {
        examService.save(exam);
        return new ResponseEntity<>(exam, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Exam> edit(@PathVariable Long id, @RequestBody Exam exam){
        Optional<Exam> currentExam = examService.findById(id);
        if (!currentExam.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        currentExam.get().setCreated_at(exam.getCreated_at());
        currentExam.get().setDuration(exam.getDuration());
        currentExam.get().setScore(exam.getScore());
        currentExam.get().setCategory(exam.getCategory());
        examService.save(currentExam.get());
        return new ResponseEntity<>(currentExam.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Exam> delete(@PathVariable Long id){
        examService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
