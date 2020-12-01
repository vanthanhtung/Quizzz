package md6.quizzz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp started_at;
    private Timestamp finished_at;
    private double score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_user_id")
    private AppUser app_user;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "exam_id")
//    private Exam exam;

    @OneToMany
    @JoinColumn(name = "record_answer_id")
    private List<RecordAnswer> recordAnswers;
}
