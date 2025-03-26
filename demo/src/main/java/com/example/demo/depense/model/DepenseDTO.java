package com.example.demo.depense.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DepenseDTO {
    private Long id;
    private String description;
    private Double amount;
    private LocalDateTime createdAt;
    private Long groupId;
    private Long payerId;
}