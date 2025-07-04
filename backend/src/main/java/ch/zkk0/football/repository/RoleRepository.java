package ch.zkk0.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.zkk0.football.model.ERole;
import ch.zkk0.football.model.Role;

/**
 * Repository interface for role entities.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Finds a role by its name.
     * @param name the role name
     * @return the Role entity
     */
    Role findByName(ERole name);
}
