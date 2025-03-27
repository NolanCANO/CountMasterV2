package com.example.demo.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("users")
public class User {
    @Id
    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private Double salary = 0.0;
    private LocalDateTime createdAt;
}
