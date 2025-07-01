package ch.zkk0.football.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zkk0.football.model.ERole;
import ch.zkk0.football.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    long countByRoleName(ERole name);

}