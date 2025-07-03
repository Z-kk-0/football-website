package ch.zkk0.football.controller;

import ch.zkk0.football.model.Play;
import ch.zkk0.football.repository.PlayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

class PlayControllerTest {

    @Mock
    private PlayRepository playRepository;

    @InjectMocks
    private PlayController playController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPlayTest() {
        Play request = new Play();
        request.setContent("Test");

        Play saved = new Play();
        saved.setId(1);
        saved.setContent("Test");

        when(playRepository.save(request)).thenReturn(saved);

        ResponseEntity<Play> response = playController.createPlay(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertSame(saved, response.getBody());
        verify(playRepository).save(request);
    }
    @Test
    void getAllPlaysTest() {
        List<Play> plays = Arrays.asList(
                new Play() {{ setId(1); setContent("Play A"); }},
                new Play() {{ setId(2); setContent("Play B"); }}
        );

        when(playRepository.findAll()).thenReturn(plays);

        ResponseEntity<List<Play>> response = playController.getAllPlays();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(plays, response.getBody());
        verify(playRepository).findAll();
    }
}
