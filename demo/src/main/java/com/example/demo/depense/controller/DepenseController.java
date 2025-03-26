package com.example.demo.depense.controller;

import com.example.demo.depense.model.DepenseDTO;
import com.example.demo.depense.service.DepenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/depenses")
@RequiredArgsConstructor
public class DepenseController {

    private final DepenseService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DepenseDTO> create(@RequestBody DepenseDTO dto) {
        return service.createDepense(dto);
    }

    @GetMapping("/{id}")
    public Mono<DepenseDTO> getById(@PathVariable Long id) {
        return service.getDepenseById(id);
    }

    @GetMapping
    public Flux<DepenseDTO> getAll() {
        return service.getAllDepenses();
    }

    @PutMapping("/{id}")
    public Mono<DepenseDTO> update(@PathVariable Long id, @RequestBody DepenseDTO dto) {
        return service.updateDepense(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.deleteDepense(id);
    }
}