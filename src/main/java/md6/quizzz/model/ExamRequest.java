package md6.quizzz.model;

import lombok.Data;

import java.util.Set;

@Data
public class ExamRequest {
    private Boolean enabled = true;
    private int duration;
    private String exam_code;
    private String exam_name;
    private int numberOfQuiz;
}
