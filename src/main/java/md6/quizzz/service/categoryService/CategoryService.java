package md6.quizzz.service.categoryService;

import md6.quizzz.model.Category;

public interface CategoryService {
    Iterable<Category> getAll();

    void save(Category category);

    boolean validate(Category category);
}
