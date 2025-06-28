package ch.zkk0.football.controller;

import java.util.List;
import java.util.Optional;

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

@RestController
@RequestMapping("/api/plays")
public class PlayController {
    @Autowired 
    PlayRepository playRepository; 

    /**
     * Retrieves all Play entities.
     *
     * @return a ResponseEntity containing the list of all Play objects and HTTP status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<Play>> getAllPlays() {
        List<Play> plays = playRepository.findAll();
        return ResponseEntity.ok(plays);
    }

    /**
     * Creates a new Play entity from the provided request body and returns the saved entity.
     *
     * @param request the Play object to be created, validated from the request body
     * @return a ResponseEntity containing the created Play and HTTP status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Play> createPlay(@Valid @RequestBody Play request) {
        Play saved = playRepository.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Updates an existing Play entity identified by the given ID with new content.
     *
     * If the Play entity with the specified ID exists, its content is updated with the value from the request body and the updated entity is returned with HTTP 200 OK. If the entity does not exist, returns HTTP 404 Not Found.
     *
     * @param Id the ID of the Play entity to update
     * @param request the Play object containing updated content
     * @return ResponseEntity containing the updated Play entity or HTTP 404 if not found
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
 * Deletes a Play entity by its ID.
 *
 * If the Play with the specified ID does not exist, returns HTTP 404 Not Found.
 * If the deletion is successful, returns HTTP 204 No Content.
 *
 * @param id the ID of the Play to delete
 * @return a ResponseEntity indicating the outcome of the deletion
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
