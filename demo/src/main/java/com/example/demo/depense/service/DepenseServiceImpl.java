package com.example.demo.depense.service;

import com.example.demo.depense.mapper.DepenseMapper;
import com.example.demo.depense.model.Depense;
import com.example.demo.depense.model.DepenseDTO;
import com.example.demo.depense.repository.DepenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DepenseServiceImpl implements DepenseService {

    private final DepenseRepository repository;
    private final DepenseMapper mapper;

    @Override
    @Transactional
    public Mono<DepenseDTO> createDepense(DepenseDTO dto) {
        Depense depense = mapper.toEntity(dto);
        depense.setCreatedAt(LocalDateTime.now());
        return repository.save(depense)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DepenseDTO> getDepenseById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Depense not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DepenseDTO> getAllDepenses() {
        return repository.findAll()
                .map(mapper::toDTO);
    }

    @Override
    @Transactional
    public Mono<DepenseDTO> updateDepense(Long id, DepenseDTO dto) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Depense not found with id: " + id)))
                .flatMap(depense -> {
                    depense.setDescription(dto.getDescription());
                    depense.setAmount(dto.getAmount());
                    depense.setGroupId(dto.getGroupId());
                    depense.setPayerId(dto.getPayerId());
                    return repository.save(depense);
                })
                .map(mapper::toDTO);
    }

    @Override
    @Transactional
    public Mono<Void> deleteDepense(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Depense not found with id: " + id)))
                .flatMap(repository::delete);
    }
}