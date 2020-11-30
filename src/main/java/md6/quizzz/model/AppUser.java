package md6.quizzz.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class AppUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String address;
    private int age;
    private String email;
    private String image;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> roles = new HashSet<>();

    public AppUser(String username, String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }



}
