package md6.quizzz.service.examService;

import md6.quizzz.model.Exam;

import java.util.Optional;

public interface ExamService {
    Iterable<Exam> findAll();

    Optional<Exam> findById(Long id);

    Exam save(Exam exam);

    void deleteById(Long id);

    boolean validate(Exam exam);
}
