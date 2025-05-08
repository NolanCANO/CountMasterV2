package com.example.demo.auth.controller;

import com.example.demo.auth.model.AuthRequest;
import com.example.demo.auth.model.AuthResponse;
import com.example.demo.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest request) {
        return authService.authenticate(request.getUsername(), request.getPassword())
                .map(token -> ResponseEntity.ok(new AuthResponse(token)))
                .defaultIfEmpty(ResponseEntity.status(401).build());
    }
    
    @PostMapping("/register")
    public Mono<ResponseEntity<AuthResponse>> register(@RequestBody AuthRequest request) {
        return authService.register(request.getUsername(), request.getEmail(), request.getPassword())
                .map(token -> ResponseEntity.ok(new AuthResponse(token)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(new AuthResponse(null, e.getMessage()))));
    }
}
