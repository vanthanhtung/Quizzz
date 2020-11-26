package md6.quizzz.service.categoryService;

import md6.quizzz.model.Category;
import md6.quizzz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean validate(Category category) {
        for (Category category1: categoryRepository.findAll()){
            if (category1.getName().equals(category.getName())) return false;
        }
        return category.getName() != null && !category.getName().equals("");
    }
}
