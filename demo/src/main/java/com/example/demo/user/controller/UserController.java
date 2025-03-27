package com.example.demo.user.controller;

import com.example.demo.user.model.UserDTO;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDTO> create(@RequestBody UserDTO dto, @RequestParam String password) {
        return service.createUser(dto, password);
    }

    @GetMapping("/{id}")
    public Mono<UserDTO> getById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping
    public Flux<UserDTO> getAll() {
        return service.getAllUsers();
    }

    @PutMapping("/{id}")
    public Mono<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO dto) {
        return service.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.deleteUser(id);
    }
}