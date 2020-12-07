package md6.quizzz.dto;

import lombok.Data;
import md6.quizzz.model.AppUser;
import md6.quizzz.model.Exam;
import md6.quizzz.model.RecordAnswer;

import java.util.Date;
import java.util.List;

@Data
public class RecordRespon {
    private Long id;
    private Date started_at;
    private Date finished_at;
    private double score;
    private AppUser appUser;
    private ExamRespon examRespon;
    private List<RecordAnswer> recordAnswers;
}
