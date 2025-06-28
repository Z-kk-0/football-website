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
import ch.zkk0.football.util.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/plays")
public class PlayController {
    @Autowired 
    PlayRepository playRepository; 

    @GetMapping
    public ResponseEntity<List<Play>> getAllPlays() {
        List<Play> plays = playRepository.findAll();
        return ResponseEntity.ok(plays);
    }

    @PostMapping
    public ResponseEntity<Play> createPlay(@Valid @RequestBody Play request) {
        Play saved = playRepository.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlay(@PathVariable Long id) {
        if (!playRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        playRepository.deleteById(id);
        return ResponseEntity.noContent().build();
}



}
