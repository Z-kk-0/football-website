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

/**
 * REST controller for user-related operations.
 * Provides endpoints for retrieving all users and updating user roles.
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
     * Updates the role of a user.
     *
     * @param userId the ID of the user
     * @param request the request body containing the new role name
     * @return ResponseEntity with status and message
     */
    @CrossOrigin
    @PutMapping("/{userId}/role")
    public ResponseEntity<?> updateRole(@PathVariable Long userId, @RequestBody RoleUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User nicht gefunden"));
        ERole eRole;
        try {
            eRole = ERole.valueOf(request.getRoleName());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Ungültiger Rollenname: " + request.getRoleName());
        }

        if (user.getRole().getName() == ERole.ROLE_ADMIN && eRole != ERole.ROLE_ADMIN) {
        long adminCount = userRepository.countByRoleName(ERole.ROLE_ADMIN);

        if (adminCount <= 1) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Der letzte Admin darf nicht entfernt werden.");
        }
    }

        Role role = roleRepository.findByName(eRole);
        if (role == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role nicht gefunden: " + request.getRoleName());
        }
        user.setRole(role);
        userRepository.save(user);
        return ResponseEntity.ok("Rolle erfolgreich geupdated auf " + request.getRoleName());

    }

    /**
     * Retrieves a list of all users.
     *
     * @return ResponseEntity with a list of UserResponse
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
