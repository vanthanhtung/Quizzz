package md6.quizzz.service.quizService;

import md6.quizzz.model.Quiz;

import java.util.Optional;

public interface QuizService {
    Iterable<Quiz> getAll();

    Optional<Quiz> getById(Long id);

    void save(Quiz quiz);

    void deleteById(Long id);
}
