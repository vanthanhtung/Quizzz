package md6.quizzz.controller;

import md6.quizzz.model.Exam;
import md6.quizzz.model.Quiz;
import md6.quizzz.repository.QuizRepository;
import md6.quizzz.service.categoryService.CategoryService;
import md6.quizzz.service.quizService.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/quizzes")
public class QuizController {
    @Autowired
    QuizService quizService;

    @Autowired
    CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<Iterable<Quiz>> findAll(){
        return new ResponseEntity<>(quizService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Quiz>> findById(@PathVariable Long id){
        Optional<Quiz> quiz = quizService.getById(id);
        if (!quiz.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Quiz> save(@RequestBody Quiz quiz){
        quizService.save(quiz);
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Quiz> edit(@PathVariable Long id, @RequestBody Quiz quiz){
        Optional<Quiz> currentQuiz = quizService.getById(id);
        if (!currentQuiz.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        currentQuiz.get().set_active(quiz.is_active());
        currentQuiz.get().setCategory(quiz.getCategory());
        currentQuiz.get().setContent(quiz.getContent());
        currentQuiz.get().setCreate_at(quiz.getCreate_at());
        currentQuiz.get().setExam(quiz.getExam());
        currentQuiz.get().setLevel(quiz.getLevel());
        currentQuiz.get().setScore(quiz.getScore());
        currentQuiz.get().setType(quiz.getType());
        quizService.save(currentQuiz.get());
        return new ResponseEntity<>(currentQuiz.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Exam> delete(@PathVariable Long id){
        quizService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
