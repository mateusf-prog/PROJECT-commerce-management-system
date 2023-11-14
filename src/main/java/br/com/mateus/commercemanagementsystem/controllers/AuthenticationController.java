package br.com.mateus.commercemanagementsystem.controllers;

import br.com.mateus.commercemanagementsystem.dto.AuthenticationDTO;
import br.com.mateus.commercemanagementsystem.dto.RegisterDTO;
import br.com.mateus.commercemanagementsystem.model.User;
import br.com.mateus.commercemanagementsystem.repository.UserRepository;
import br.com.mateus.commercemanagementsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUSer = new User(data.login(), encryptedPassword, data.role());

        this.userRepository.save(newUSer);

        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity listUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }
}
