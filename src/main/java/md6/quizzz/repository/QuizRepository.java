package md6.quizzz.repository;

import md6.quizzz.model.Category;
import md6.quizzz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Iterable<Quiz> findByCategory(Category category);
}
