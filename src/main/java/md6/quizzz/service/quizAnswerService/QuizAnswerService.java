package md6.quizzz.service.quizAnswerService;

import md6.quizzz.model.Quiz;
import md6.quizzz.model.QuizAnswer;

import java.util.Optional;

public interface QuizAnswerService {
    Iterable<QuizAnswer> getAll();

    Optional<QuizAnswer> getById(Long id);

    void save(QuizAnswer quizAnswer);

    void deleteById(Long id);
}
