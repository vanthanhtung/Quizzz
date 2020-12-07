package md6.quizzz.dto;

import lombok.Data;
import md6.quizzz.model.Exam;
import md6.quizzz.model.Quiz;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class ExamRespon implements Comparable<ExamRespon>{
    private Long id;
    private Boolean enabled = true;

    private int duration;
    private String exam_code;
    private String exam_name;
    private double score;

    private Date started_at;
    List<Quiz> quizSet;

    @Override
    public int compareTo(ExamRespon o) {
        return this.getStarted_at().compareTo(o.getStarted_at());
    }
}
