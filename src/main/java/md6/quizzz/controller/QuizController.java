package md6.quizzz.controller;

import md6.quizzz.model.Category;
import md6.quizzz.model.Quiz;
import md6.quizzz.repository.QuizRepository;
import md6.quizzz.service.categoryService.CategoryService;
import md6.quizzz.service.quizService.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Iterable<Quiz>> getAll(){
        return new ResponseEntity<>(quizService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Quiz>> getById(@PathVariable Long id){
        Optional<Quiz> quiz = quizService.getById(id);
        if (!quiz.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Quiz> add(@RequestBody Quiz quiz){
        quizService.save(quiz);
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }

    @GetMapping("/categories/{name}")
    public ResponseEntity<List<Quiz>> getAllByCategory_Name(@PathVariable String name){
        return new ResponseEntity<>(quizService.findAllByCategory_Name(name), HttpStatus.OK);
    }


}
