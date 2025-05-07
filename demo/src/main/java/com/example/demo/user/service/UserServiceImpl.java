package com.example.demo.user.service;

import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.User;
import com.example.demo.user.model.UserDTO;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Mono<UserDTO> createUser(UserDTO dto, String password) {
        User user = mapper.toEntity(dto);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());
        return repository.save(user).map(mapper::toDTO);
    }

    @Override
    public Mono<UserDTO> getUserById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Flux<UserDTO> getAllUsers() {
        return repository.findAll().map(mapper::toDTO);
    }

    @Override
    public Mono<UserDTO> updateUser(Long id, UserDTO dto) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with id: " + id)))
                .flatMap(user -> {
                    mapper.updateEntityFromDTO(dto, user);
                    return repository.save(user);
                }).map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with id: " + id)))
                .flatMap(repository::delete);
    }
}
