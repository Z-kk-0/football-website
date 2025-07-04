package ch.zkk0.football.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Response object containing JWT token and user details for authentication.
 */
@Setter
@Getter
public class JwtResponse {
    /**
     * The JWT access token.
     */
    private String token;
    /**
     * The token type (default is "Bearer").
     */
    private String type = "Bearer";
    /**
     * The user's unique identifier.
     */
    private Long id;
    /**
     * The username of the authenticated user.
     */
    private String username;
    /**
     * The email address of the authenticated user.
     */
    private String email;
    /**
     * The list of roles assigned to the user.
     */
    private List<String> roles;

    /**
     * Constructs a new JwtResponse with the given details.
     *
     * @param accessToken the JWT access token
     * @param id the user ID
     * @param userName the username
     * @param email the email address
     * @param roles the list of user roles
     */
    public JwtResponse(String accessToken,
            Long id, String userName,
            String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = userName;
        this.email = email;
        this.roles = roles;
    }
}
