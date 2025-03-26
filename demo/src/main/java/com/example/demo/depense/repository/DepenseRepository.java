package com.example.demo.depense.repository;

import com.example.demo.depense.model.Depense;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface DepenseRepository extends ReactiveCrudRepository<Depense, Long> {
    Flux<Depense> findAllByGroupId(Long groupId);
}