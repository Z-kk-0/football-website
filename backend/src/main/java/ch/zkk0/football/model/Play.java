package ch.zkk0.football.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a play or action in the system.
 */
@Getter
@Setter
@Entity
public class Play {
    /**
     * The unique identifier for the play.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * The content or description of the play.
     */
    @NotBlank
    String content;
}
