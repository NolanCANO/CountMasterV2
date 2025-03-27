package com.example.demo.user.service;

import com.example.demo.user.model.UserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserDTO> createUser(UserDTO dto, String password);
    Mono<UserDTO> getUserById(Long id);
    Flux<UserDTO> getAllUsers();
    Mono<UserDTO> updateUser(Long id, UserDTO dto);
    Mono<Void> deleteUser(Long id);
}
