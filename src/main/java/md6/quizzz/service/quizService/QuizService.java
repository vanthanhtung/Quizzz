package md6.quizzz.service.quizService;

import md6.quizzz.model.Category;
import md6.quizzz.model.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizService {
    Iterable<Quiz> getAll();

    Optional<Quiz> getById(Long id);

    List<Quiz> findAllByCategory_Name(String name);

    Iterable<Quiz> findAllByCategory_Id(Long id);

    void save(Quiz quiz);

    void deleteById(Long id);
}
