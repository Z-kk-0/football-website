package ch.zkk0.football.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.zkk0.football.model.ERole;
import ch.zkk0.football.model.Role;
import ch.zkk0.football.model.User;
import ch.zkk0.football.repository.RoleRepository;
import ch.zkk0.football.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    /**
     * Updates the role of a user in a transactional way.
     * Checks if the last admin would be removed and prevents it.
     * @param userId the user ID
     * @param roleName the new role name (e.g. "ROLE_ADMIN")
     */
    @Transactional
    public void updateUserRole(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User nicht gefunden"));
        ERole eRole;
        try {
            eRole = ERole.valueOf(roleName);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ungültiger Rollenname: " + roleName);
        }
        if (user.getRole().getName() == ERole.ROLE_ADMIN && eRole != ERole.ROLE_ADMIN) {
            long adminCount = userRepository.countByRoleName(ERole.ROLE_ADMIN);
            if (adminCount <= 1) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Der letzte Admin darf nicht entfernt werden.");
            }
        }
        Role role = roleRepository.findByName(eRole);
        if (role == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role nicht gefunden: " + roleName);
        }
        user.setRole(role);
        userRepository.save(user);
    }
}
