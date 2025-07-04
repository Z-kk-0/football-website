package ch.zkk0.football.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Request object for user signup containing username, email, and password.
 */
@Getter
@Setter
public class SignupRequest {
    /**
     * The username for the new user.
     */
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    /**
     * The email address for the new user.
     */
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    /**
     * The password for the new user.
     */
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
