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
import lombok.Getter;
import lombok.Setter;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public static class RoleUpdateRequest {
        private String roleName;

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }
    }
public static class UserResponse {
    private int id;
    private String username;
    private String email;
    private String role;

    public UserResponse(int id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // Getter (Setter nicht nötig, weil wir nur lesen)
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
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
        Role role = roleRepository.findByName(eRole);
        if (role == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role nicht gefunden: " + request.getRoleName());
        }
        user.setRole(role);
        userRepository.save(user);
        return ResponseEntity.ok("Rolle erfolgreich geupdated auf " + request.getRoleName());

    }

@CrossOrigin
@GetMapping
public ResponseEntity<List<UserResponse>> getAllUsers() {
    List<UserResponse> users = userRepository.findAll().stream()
        .map(u -> new UserResponse(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getRole().getName().name() 
        ))
        .collect(Collectors.toList());

   
    return ResponseEntity.ok(users);
}
}
