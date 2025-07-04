package ch.zkk0.football.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a user in the system.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * The username of the user.
     */
    @NotBlank
    private String username;
    /**
     * The email address of the user.
     */
    @NotBlank
    private String email;
    /**
     * The hashed password of the user.
     */
    @NotBlank
    private String password;

    /**
     * Constructs a new User with the given name, email, and password.
     * @param name the username
     * @param email the email address
     * @param password the password
     */
    public User(String name, String email, String password) {
        this.username = name;
        this.email = email;
        this.password = password;
    }

    /**
     * The role assigned to the user.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
