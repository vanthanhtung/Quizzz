package md6.quizzz.repository;

import md6.quizzz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findAllByCategory_Name(String name);
    Iterable<Quiz> findAllByCategory_Id(Long id);
}