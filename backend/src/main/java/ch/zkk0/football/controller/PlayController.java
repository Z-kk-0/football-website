package ch.zkk0.football.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zkk0.football.model.Play;
import ch.zkk0.football.repository.PlayRepository;

import jakarta.validation.Valid;

/**
 * REST controller for Play objects (plays).
 *
 * Provides endpoints to create, edit, delete, and retrieve plays.
 * The business logic is simple, as plays only contain a string.
 *
 * Transactions are not needed here, as each operation is atomic (one play per request).
 */
@RestController
@RequestMapping("/api/plays")
public class PlayController {
    @Autowired 
    PlayRepository playRepository; 

    /**
     * Returns all stored plays.
     *
     * @return List of all plays (status 200)
     */
    @GetMapping
    public ResponseEntity<List<Play>> getAllPlays() {
        List<Play> plays = playRepository.findAll();
        return ResponseEntity.ok(plays);
    }

    /**
     * Creates a new play.
     *
     * @param request Play object (content only)
     * @return The saved play (status 201)
     */
    @PostMapping
    public ResponseEntity<Play> createPlay(@Valid @RequestBody Play request) {
        Play saved = playRepository.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Updates a play by ID.
     *
     * @param Id ID of the play to update
     * @param request New play object (content only)
     * @return The updated play (status 200) or 404 if not found
     */
    @PutMapping("/{Id}")
    public ResponseEntity<Play> updatePlay(@PathVariable Long Id, @Valid @RequestBody Play request) {
        return playRepository.findById(Id)
        .map(ex -> {
            ex.setContent(request.getContent());
            Play updated = playRepository.save(ex);
            return ResponseEntity.ok(updated);
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a play by ID.
     *
     * @param id ID of the play to delete
     * @return 204 No Content or 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlay(@PathVariable Long id) {
        if (!playRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        playRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
