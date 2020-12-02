package md6.quizzz.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"category", "answers"})
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    @Max(3)
    private Integer type;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ELevel level;

    @Column(columnDefinition = "boolean default true")
    private boolean is_active;

    @NotBlank
    @NotNull
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("quizList")
    private Category category;

    @OneToMany(targetEntity = QuizAnswer.class, mappedBy = "quiz")
    @JsonIgnoreProperties("quiz")
    private List<QuizAnswer> answers;

    @OneToMany(targetEntity = RecordAnswer.class, mappedBy = "quiz")
    @JsonIgnoreProperties("quiz")
    private List<RecordAnswer> recordAnswers;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "quiz_exam",
//            joinColumns = { @JoinColumn(name = "exam_id") },
//            inverseJoinColumns = {@JoinColumn(name = "quiz_id") })
//    private List<Exam> exam;
}
