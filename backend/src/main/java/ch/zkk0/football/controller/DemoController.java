package ch.zkk0.football.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class DemoController {
    public List<String> list = new ArrayList<>();

    @CrossOrigin
    @GetMapping("/public")
    public ResponseEntity<List<String>> publicEndpoint() {
        return ResponseEntity.ok(list);
    }

    @CrossOrigin
    @PostMapping("/public")
    public ResponseEntity<List<String>> postPublicEndpoint(@RequestBody String message) {
        list.add(message);
        return ResponseEntity.ok(list);
    }

    @CrossOrigin
    @GetMapping("/private")
    public ResponseEntity<String> privateEndpoint() {
        return ResponseEntity.ok("This is a private Endpoint");
    }
}
