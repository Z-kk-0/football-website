package ch.zkk0.football.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ch.zkk0.football.dto.JwtResponse;
import ch.zkk0.football.dto.LoginRequest;
import ch.zkk0.football.dto.MessageResponse;
import ch.zkk0.football.dto.SignupRequest;
import ch.zkk0.football.model.ERole;
import ch.zkk0.football.model.Role;
import ch.zkk0.football.model.User;
import ch.zkk0.football.repository.RoleRepository;
import ch.zkk0.football.repository.UserRepository;
import ch.zkk0.football.security.JwtUtils;
import ch.zkk0.football.security.UserDetailsImpl;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateToken(request.getUsername());
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));
        } catch (Exception e) {
            logger.error("Cannot authenticate user: {}", e);
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username or password incorrect!"));
        }
    }

@PostMapping("/signup")
public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
        return ResponseEntity.badRequest()
                .body(new MessageResponse("Error: Username is already taken!"));
    }
    if (userRepository.existsByEmail(request.getEmail())) {
        return ResponseEntity.badRequest()
                .body(new MessageResponse("Error: Email is already in use!"));
    }

    User user = new User(
        request.getUsername(),
        request.getEmail(),
        encoder.encode(request.getPassword())
    );

    ERole assignedRole = userRepository.count() == 0 ? ERole.ROLE_ADMIN : ERole.ROLE_PLAYER;
    Role role = roleRepository.findByName(assignedRole);

    if (role == null) {
        return ResponseEntity
                .internalServerError()
                .body(new MessageResponse("Error: Rolle nicht gefunden"));
    }

    user.setRole(role);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
}

}
