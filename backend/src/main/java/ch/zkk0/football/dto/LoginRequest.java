package ch.zkk0.football.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Request object for user login containing username and password.
 */
@Setter
@Getter
public class LoginRequest {
    /**
     * The username of the user attempting to log in.
     */
    private String username;
    /**
     * The password of the user attempting to log in.
     */
    private String password;
}
