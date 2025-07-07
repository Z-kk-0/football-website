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

/**
 * Service for user operations (e.g., role changes).
 *
 * Encapsulates business logic and ensures that critical changes (such as removing admin rights) are performed transactionally and safely.
 *
 * The method updateUserRole() is @Transactional to guarantee that the admin check and saving are atomic.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    /**
     * Sets a user's role (e.g., by an admin).
     *
     * The transaction ensures that the last admin cannot be removed.
     *
     * @param userId ID of the user
     * @param roleName New role (e.g., "ROLE_ADMIN")
     * @throws ResponseStatusException if user/role not found or last admin would be removed
     */
    @Transactional
    public void updateUserRole(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        ERole eRole;
        try {
            eRole = ERole.valueOf(roleName);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role name: " + roleName);
        }
        if (user.getRole().getName() == ERole.ROLE_ADMIN && eRole != ERole.ROLE_ADMIN) {
            long adminCount = userRepository.countByRoleName(ERole.ROLE_ADMIN);
            if (adminCount <= 1) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The last admin cannot be removed.");
            }
        }
        Role role = roleRepository.findByName(eRole);
        if (role == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found: " + roleName);
        }
        user.setRole(role);
        userRepository.save(user);
    }
}
