package md6.quizzz.controller;

import md6.quizzz.model.Category;
import md6.quizzz.service.categoryService.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<Iterable<Category>> findAll(){
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Category> save(@RequestBody Category category) {
        if (categoryService.validate(category)) {
            categoryService.save(category);return new ResponseEntity<>(category, HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
