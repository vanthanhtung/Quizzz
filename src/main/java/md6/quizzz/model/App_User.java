package md6.quizzz.model;

import lombok.Data;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private User_Role role;
}
