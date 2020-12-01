package md6.quizzz.service.quizAnswerService;

import md6.quizzz.model.QuizAnswer;
import md6.quizzz.repository.QuizAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizAnswerServiceImpl implements QuizAnswerService{
    @Autowired
    public QuizAnswerRepository quizAnswerRepository;

    @Override
    public Iterable<QuizAnswer> getAll() {
        return quizAnswerRepository.findAll();
    }

    @Override
    public Optional<QuizAnswer> getById(Long id) {
        return quizAnswerRepository.findById(id);
    }

    @Override
    public void save(QuizAnswer quizAnswer) {
        quizAnswerRepository.save(quizAnswer);
    }

    @Override
    public void deleteById(Long id) {
        quizAnswerRepository.deleteById(id);
    }
}
