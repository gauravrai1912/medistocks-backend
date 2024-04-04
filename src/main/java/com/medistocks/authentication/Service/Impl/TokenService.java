package com.medistocks.authentication.Service.Impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.Entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SuppressWarnings("deprecation")
@Service
public class TokenService {
    
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    // Method to generate JWT token
    public String generateToken(User user) {
        // Set the expiration time of the token (e.g., 1 hour from now)
        long expirationTimeMillis = System.currentTimeMillis() + 3600000; // 1 hour
        Date expirationDate = new Date(expirationTimeMillis);

        // Generate the JWT token
        String token = Jwts.builder()
                .setSubject(user.getEmail()) // Set the subject (in this case, the user's email)
                .setExpiration(expirationDate) // Set the token expiration time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Sign the token with the secret key using HMAC SHA-256 algorithm
                .compact();

        return token;
    }
}
