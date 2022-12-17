package mk.finki.ukim.mk.lab.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    /*@Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;*/
    @Convert(converter = UserFullNameConverter.class)
    private UserFullName fullName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    //ova mi pravese ciklicna zavisnost
    /*@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ShoppingCart> carts;*/

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Order> orders;

    public User() {
    }

    public User(UserFullName fullName, String username, String password, LocalDate dateOfBirth) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }
}
