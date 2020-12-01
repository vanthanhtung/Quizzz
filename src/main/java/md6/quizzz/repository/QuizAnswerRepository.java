package md6.quizzz.repository;

import md6.quizzz.model.Quiz;
import md6.quizzz.model.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer,Long> {
}
