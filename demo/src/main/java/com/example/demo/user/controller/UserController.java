package com.example.demo.user.controller;

import com.example.demo.user.model.UserDTO;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;
    
    @GetMapping("/api/user/profile")
    public Mono<ResponseEntity<UserDTO>> getCurrentUserProfile() {
        return ReactiveSecurityContextHolder.getContext()
                .doOnNext(ctx -> logger.debug("Security context: {}", ctx))
                .map(SecurityContext::getAuthentication)
                .doOnNext(auth -> logger.debug("Authentication: {}, Principal: {}", 
                    auth.getClass().getSimpleName(), auth.getPrincipal()))
                .map(authentication -> authentication.getName())
                .doOnNext(username -> logger.debug("Username from authentication: {}", username))
                .flatMap(username -> service.getUserByUsername(username)
                    .map(userDTO -> ResponseEntity.ok(userDTO))
                    .onErrorResume(e -> {
                        logger.error("Error retrieving user profile: {}", e.getMessage());
                        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                    })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping("/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDTO> create(@RequestBody UserDTO dto, @RequestParam String password) {
        return service.createUser(dto, password);
    }

    @GetMapping("/api/users/{id}")
    public Mono<UserDTO> getById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping("/api/users")
    public Flux<UserDTO> getAll() {
        return service.getAllUsers();
    }

    @PutMapping("/api/users/{id}")
    public Mono<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO dto) {
        return service.updateUser(id, dto);
    }

    @DeleteMapping("/api/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.deleteUser(id);
    }
}