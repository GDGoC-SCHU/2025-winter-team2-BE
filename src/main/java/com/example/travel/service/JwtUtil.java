package com.example.travel.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtUtil {

    private Key secretKey;
    private final StringRedisTemplate redisTemplate;

    public JwtUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String generateToken(String email) {
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                .signWith(secretKey)
                .compact();

        // Store token in Redis with an expiration time
        redisTemplate.opsForValue().set(email, token, 60, TimeUnit.MINUTES);

        return token;
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // 이메일(subject) 추출
    }



    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateRefreshToken(String email) {
        String refreshToken = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7일 후 만료
                .signWith(secretKey)
                .compact();

        redisTemplate.opsForValue().set("refresh_" + email, refreshToken, 7, TimeUnit.DAYS);
        return refreshToken;
    }

    public boolean validateRefreshToken(String refreshToken) {
        String email = extractEmail(refreshToken);
        String storedToken = redisTemplate.opsForValue().get("refresh_" + email);
        return refreshToken.equals(storedToken);
    }


    public void invalidateToken(String email) {
        redisTemplate.delete(email);
    }
}
