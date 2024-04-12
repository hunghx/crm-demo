package crm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Please provide a valid e-mail")
    @NotEmpty(message = "Please provide an e-mail")
    private String email;

    private String fullName;

    private String password;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "birth_day")
    private LocalDate birthDay;
    private boolean sex;
    private String address;
    @Column(name = "phone_number")
    private String phone;

    private int enabled;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public int getColumnCount() {
        return getClass().getDeclaredFields().length;
    }

    public int getRole_id() {
        return role.getId();
    }

    public String getRole_name() {
        return role.getName();
    }


}
