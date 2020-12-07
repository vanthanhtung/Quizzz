package md6.quizzz.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "boolean default true")
    private Boolean enabled = true;

    private int duration;

    @NotBlank
    @NotNull
    @Column(unique=true)
    private String exam_code;

    @NotBlank
    @NotNull
    private String exam_name;
    private double score;

    private Date started_at;


    @ManyToMany()
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<Quiz> quizSet;
}
