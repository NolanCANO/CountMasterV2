package com.example.demo.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String message;
    
    public AuthResponse(String token) {
        this.token = token;
        this.message = "Success";
    }
}