package md6.quizzz.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"quizList"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("category")
    @JsonBackReference
    private List<Quiz> quizList;

}
