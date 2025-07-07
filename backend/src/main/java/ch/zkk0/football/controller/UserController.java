package ch.zkk0.football.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ch.zkk0.football.model.ERole;
import ch.zkk0.football.model.Role;
import ch.zkk0.football.model.User;
import ch.zkk0.football.repository.RoleRepository;
import ch.zkk0.football.repository.UserRepository;
import ch.zkk0.football.service.UserService;

/**
 * REST controller for user operations (user management, roles).
 *
 * Provides endpoints to retrieve all users and to change user roles.
 * The business logic for role changes is delegated to UserService (transaction protects admin role).
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    /**
     * Repository for user entities.
     */
    @Autowired
    UserRepository userRepository;
    /**
     * Repository for role entities.
     */
    @Autowired
    RoleRepository roleRepository;
    /**
     * Service for user-related operations.
     */
    @Autowired
    UserService userService;

    /**
     * Request body for updating a user's role.
     */
    public static class RoleUpdateRequest {
        private String roleName;

        /**
         * Gets the name of the role.
         * @return the role name
         */
        public String getRoleName() {
            return roleName;
        }

        /**
         * Sets the name of the role.
         * @param roleName the role name
         */
        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }
    }

    /**
     * Response object for user information.
     */
    public static class UserResponse {
        private int id;
        private String username;
        private String email;
        private String role;

        /**
         * Constructs a new UserResponse.
         * @param id the user ID
         * @param username the username
         * @param email the email address
         * @param role the role name
         */
        public UserResponse(int id, String username, String email, String role) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.role = role;
        }
        /**
         * Gets the user ID.
         * @return the user ID
         */
        public int getId() {
            return id;
        }
        /**
         * Gets the username.
         * @return the username
         */
        public String getUsername() {
            return username;
        }
        /**
         * Gets the email address.
         * @return the email address
         */
        public String getEmail() {
            return email;
        }
        /**
         * Gets the role name.
         * @return the role name
         */
        public String getRole() {
            return role;
        }
    }

    /**
     * Updates the role of a user (admin only).
     *
     * @param userId ID of the user
     * @param request New role
     * @return Success message or error
     */
    @CrossOrigin
    @PutMapping("/{userId}/role")
    public ResponseEntity<?> updateRole(@PathVariable Long userId, @RequestBody RoleUpdateRequest request) {
        userService.updateUserRole(userId, request.getRoleName());
        return ResponseEntity.ok("Role successfully updated to " + request.getRoleName());
    }
    /**
     * Returns all users as a list.
     *
     * @return List of all users (id, username, email, role)
     */
    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userRepository.findAll().stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getRole().getName().name()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }
}
