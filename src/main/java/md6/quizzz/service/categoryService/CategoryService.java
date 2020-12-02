package md6.quizzz.service.categoryService;

import md6.quizzz.model.Category;

import java.util.Optional;

public interface CategoryService {
    Iterable<Category> getAll();

    Optional<Category> findById(Long id);

    void save(Category category);

    void deleteById(Long id);

    boolean validate(Category category);

    Optional<Category> findByName(String name);
}
