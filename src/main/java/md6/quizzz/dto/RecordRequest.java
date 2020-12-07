package md6.quizzz.dto;

import lombok.Data;
import md6.quizzz.model.Exam;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
public class RecordRequest {
    private Exam exam;
    private List<Long> recordAnswer;
//    private List<String> recordAnswerContent;
}
