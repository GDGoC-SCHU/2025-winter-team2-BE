package com.example.travel.controller;

import com.example.travel.domain.User;
import com.example.travel.service.JwtUtil;
import com.example.travel.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.register(request.getEmail(), request.getPassword(), request.getNickname());
        return ResponseEntity.ok("User registered successfully");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request.getEmail(), request.getPassword()));
    }

    // 소셜 로그인
    @PostMapping("/social-login")
    public ResponseEntity<String> socialLogin(@RequestBody SocialLoginRequest request) {
        String token = userService.socialLogin(request.getProvider(), request.getAccessToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        jwtUtil.invalidateToken(token);
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
        String email = jwtUtil.extractEmail(refreshToken);
        String newAccessToken = jwtUtil.generateToken(email);
        return ResponseEntity.ok(newAccessToken);
    }


    // 마이프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        User user = userService.getUserByToken(token);
        if (user == null) {
            return ResponseEntity.status(404).build(); // User not found
        }

        return ResponseEntity.ok(user);
    }


    @Getter
    @Setter
    // DTO 클래스들
    public static class RegisterRequest {
        private String email;
        private String password;
        private String nickname;

        // Getters and Setters
    }

    @Getter
    @Setter
    public static class LoginRequest {
        private String email;
        private String password;

        // Getters and Setters
    }

    @Getter
    @Setter
    public static class SocialLoginRequest {
        private String provider;
        private String accessToken;

        // Getters and Setters
    }

}