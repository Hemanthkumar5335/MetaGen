package com.metagen.userservice.service;



import io.jsonwebtoken.security.Keys;
import java.util.Base64;

public class GenerateJwtKey {
    public static void main(String[] args) {
        // Generate a secure 512-bit key
        String key = Base64.getEncoder().encodeToString(Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512).getEncoded());
        System.out.println("Generated Secure JWT Key: " + key);
    }
}

