package ch.zkk0.football.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ch.zkk0.football.model.ERole;
import ch.zkk0.football.model.Role;
import ch.zkk0.football.model.User;
import ch.zkk0.football.repository.RoleRepository;
import ch.zkk0.football.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
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
}
