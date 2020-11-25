package md6.quizzz.service.categoryService;

import md6.quizzz.model.Category;
import md6.quizzz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public boolean validate(Category category) {
        return category.getName() != null && !category.getName().equals("");
    }
}
