package md6.quizzz.service.quizService;

import md6.quizzz.model.Category;
import md6.quizzz.model.Quiz;
import md6.quizzz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService{
    @Autowired
    QuizRepository quizRepository;

    @Override
    public Iterable<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @Override
    public Optional<Quiz> getById(Long id) {
        return quizRepository.findById(id);
    }

    @Override
    public List<Quiz> findAllByCategory_Name(String name) {
        return quizRepository.findAllByCategory_Name(name);
    }

    @Override
    public Iterable<Quiz> findAllByCategory_Id(Long id) {
        return quizRepository.findAllByCategory_Id(id);
    }

    @Override
    public void save(Quiz quiz) {
        quizRepository.save(quiz);
    }

    @Override
    public void deleteById(Long id) {
        quizRepository.deleteById(id);
    }
}
