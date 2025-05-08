package com.example.demo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret:defaultSecretKeyJwtTokenProviderCountMasterApplication}")
    private String secret;
    
    @Value("${jwt.expiration:86400}")
    private long validityInSeconds; // 24 heures par défaut
    
    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInSeconds * 1000);
        
        Map<String, Object> claims = new HashMap<>();
        
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)  // Stocke le nom d'utilisateur comme sujet du token
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String getUsernameFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            // Utiliser le sujet (subject) du token pour récupérer le nom d'utilisateur
            String username = claims.getSubject();
            logger.debug("Username extracted from JWT token: {}", username);
            return username;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Error extracting username from token: {}", e.getMessage());
            return null;
        }
    }
    
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }
}