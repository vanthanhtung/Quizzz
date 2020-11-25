package md6.quizzz.service.categoryService;

import md6.quizzz.model.Category;

public interface CategoryService {
    void save(Category category);

    boolean validate(Category category);
}
