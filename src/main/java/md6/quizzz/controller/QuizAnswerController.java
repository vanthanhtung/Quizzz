package md6.quizzz.controller;

import md6.quizzz.model.Category;
import md6.quizzz.model.Quiz;
import md6.quizzz.model.QuizAnswer;
import md6.quizzz.service.quizAnswerService.QuizAnswerService;
import md6.quizzz.service.quizService.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/answers")
public class QuizAnswerController {
    @Autowired
    QuizAnswerService quizAnswerService;

    @Autowired
    QuizService QuizService;

    @GetMapping()
    public ResponseEntity<Iterable<QuizAnswer>> getAll() {
        return new ResponseEntity<>(quizAnswerService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<QuizAnswer>> getById(@PathVariable Long id) {
        Optional<QuizAnswer> quizAnswer = quizAnswerService.getById(id);
        if (!quizAnswer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(quizAnswer, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<QuizAnswer> add(@RequestBody QuizAnswer quizAnswer) {
        quizAnswerService.save(quizAnswer);
        return new ResponseEntity<>(quizAnswer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<QuizAnswer> delete(@PathVariable("id") Long id){
        quizAnswerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
