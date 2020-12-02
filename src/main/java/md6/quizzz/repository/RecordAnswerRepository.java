package md6.quizzz.repository;

import md6.quizzz.model.RecordAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordAnswerRepository extends JpaRepository<RecordAnswer, Long> {
}
