package md6.quizzz.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class App_User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String address;
    private int age;
    private String email;
    private String image;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<User_Role> roles = new HashSet<>();

    public App_User(String username,String email,  String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public App_User() {

    }

    public App_User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setRoles(Set<User_Role> roles) {
        this.roles = roles;
    }

    public Set<User_Role> getRoles() {
        return roles;
    }

    public Long getId() {
        return id;
    }
}
