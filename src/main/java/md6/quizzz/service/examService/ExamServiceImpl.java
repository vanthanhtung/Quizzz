package md6.quizzz.service.examService;

import md6.quizzz.model.Category;
import md6.quizzz.model.Exam;
import md6.quizzz.model.ExamRequest;
import md6.quizzz.model.Quiz;
import md6.quizzz.repository.CategoryRepository;
import md6.quizzz.repository.ExamRepository;
import md6.quizzz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    ExamRepository examRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    CategoryRepository categoryRepository;

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
    @Transactional
    public Exam save(ExamRequest examRequest) {
        Exam currentExam = new Exam();
        currentExam.setDuration(examRequest.getDuration());
        currentExam.setEnabled(examRequest.getEnabled());
        currentExam.setExam_code(examRequest.getExam_code());
        currentExam.setExam_name(examRequest.getExam_name());
        currentExam.setScore(examRequest.getScore());
        int numberOfQuiz = examRequest.getNumberOfQuiz();

        Category category = categoryRepository.findByName(examRequest.getCategory()).get();
        List<Quiz> list = (List<Quiz>) quizRepository.findByCategory(category);
        Set<Quiz> realQuizList = new HashSet<>();

        while (realQuizList.size() < numberOfQuiz) {
            int randomIndex = (int) Math.floor(Math.random()*list.size());
            realQuizList.add(list.get(randomIndex));
        }
        currentExam.setQuizSet(realQuizList);
        return examRepository.save(currentExam);
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
