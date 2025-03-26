package com.example.demo.depense.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("depenses")
public class Depense {
    @Id
    private Long id;
    private String description;
    private Double amount;
    private LocalDateTime createdAt;
    private Long groupId;
    private Long payerId;
}