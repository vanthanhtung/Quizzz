package md6.quizzz.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Record_Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "record_id")
    private Record record;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_answer_id")
    private Quiz_Answer quiz_answer;
}
