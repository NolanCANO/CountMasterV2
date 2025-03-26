package com.example.demo.depense.service;

import com.example.demo.depense.model.DepenseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DepenseService {
    Mono<DepenseDTO> createDepense(DepenseDTO dto);
    Mono<DepenseDTO> getDepenseById(Long id);
    Flux<DepenseDTO> getAllDepenses();
    Mono<DepenseDTO> updateDepense(Long id, DepenseDTO dto);
    Mono<Void> deleteDepense(Long id);
}