package ch.zkk0.football.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zkk0.football.model.ERole;
import ch.zkk0.football.model.User;

/**
 * Repository interface for user entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by username.
     * @param username the username
     * @return an Optional containing the user if found
     */
    Optional<User> findByUsername(String username);
    /**
     * Checks if a user exists by username.
     * @param username the username
     * @return true if exists, false otherwise
     */
    Boolean existsByUsername(String username);
    /**
     * Checks if a user exists by email.
     * @param email the email address
     * @return true if exists, false otherwise
     */
    Boolean existsByEmail(String email);
    /**
     * Counts the number of users with a specific role.
     * @param name the role name
     * @return the count of users with the given role
     */
    long countByRoleName(ERole name);

}