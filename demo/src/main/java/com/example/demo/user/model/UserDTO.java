package com.example.demo.user.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Double salary;
    private LocalDateTime createdAt;
}
