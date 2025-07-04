package ch.zkk0.football.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a user role in the system.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    /**
     * The unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the role (as an enum value).
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    /**
     * Constructs a new Role with the given name.
     * @param name the role name
     */
    public Role(ERole name) {
        this.name = name;
    }

    /**
     * Returns the string representation of the role name.
     * @return the role name as a string
     */
    public String toString() {
        return name.toString();
    }
}
