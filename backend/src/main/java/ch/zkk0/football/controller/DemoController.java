package ch.zkk0.football.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class DemoController {
    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("This is a public Endpoint");
    }
    @GetMapping("/private")
    public ResponseEntity<String> privateEndpoint() {
        return ResponseEntity.ok("This is a private Endpoint");
    }
}
