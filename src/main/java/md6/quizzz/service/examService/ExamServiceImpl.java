package md6.quizzz.service.examService;

import md6.quizzz.model.Exam;
import md6.quizzz.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    ExamRepository examRepository;

    @Override
    public Iterable<Exam> findAll() {
        return examRepository.findByEnabledIsTrue();
    }

    @Override
    public Optional<Exam> findById(Long id) {
        return examRepository.findById(id);
    }

    @Override
    public Exam save(Exam exam) {
        examRepository.save(exam);
        return exam;
    }

    @Override
    public void deleteById(Long id) {
        examRepository.deleteById(id);
    }

    @Override
    public boolean validate(Exam exam) {
        if (exam.getDuration() <= 0) return false;
        if (exam.getExam_code() == null || exam.getExam_code().equals("")) return false;
        if (exam.getExam_name() == null || exam.getExam_name().equals("")) return false;
        return exam.getScore() > 0;
    }
}
