package com.example.demo.auth.service;

import com.example.demo.user.model.User;
import com.example.demo.user.model.UserDTO;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public Mono<String> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPasswordHash()))
                .map(user -> jwtService.generateToken(user.getUsername()))
                .switchIfEmpty(Mono.empty());
    }

    public Mono<String> register(String username, String email, String password) {
        // Vérifier si l'utilisateur existe déjà
        return userRepository.findByUsername(username)
                .flatMap(existingUser -> Mono.<String>error(new RuntimeException("Ce nom d'utilisateur est déjà utilisé")))
                .switchIfEmpty(userRepository.findByEmail(email)
                        .flatMap(existingUser -> Mono.<String>error(new RuntimeException("Cette adresse e-mail est déjà utilisée")))
                        .switchIfEmpty(createNewUser(username, email, password)));
    }

    private Mono<String> createNewUser(String username, String email, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPasswordHash(passwordEncoder.encode(password));
        newUser.setSalary(0.0);
        newUser.setCreatedAt(LocalDateTime.now());
        
        return userRepository.save(newUser)
                .map(savedUser -> jwtService.generateToken(savedUser.getUsername()));
    }
}