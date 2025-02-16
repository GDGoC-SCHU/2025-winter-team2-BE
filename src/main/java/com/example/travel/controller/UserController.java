package com.example.travel.controller;

import com.example.travel.domain.User;
import com.example.travel.service.JwtUtil;
import com.example.travel.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // ✅ 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
        }

        userService.register(
                request.getEmail(),
                request.getPassword(),
                request.getBirthYear(),
                request.getGender()
        );

        return ResponseEntity.ok("User registered successfully");
    }

    // ✅ 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request.getEmail(), request.getPassword()));
    }

    // ✅ 소셜 로그인
    @PostMapping("/social-login")
    public ResponseEntity<String> socialLogin(@RequestBody SocialLoginRequest request) {
        String token = userService.socialLogin(request.getProvider(), request.getAccessToken());
        return ResponseEntity.ok(token);
    }

    // ✅ 로그아웃 (클라이언트 측에서 토큰 삭제)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token format");
        }

        // 보통 JWT는 서버에서 직접 무효화할 수 없으므로, 클라이언트가 삭제하도록 응답 반환
        return ResponseEntity.ok("Logged out successfully. Please delete your token.");
    }

    // ✅ 리프레시 토큰 발급
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid refresh token format");
        }

        refreshToken = refreshToken.substring(7); // "Bearer " 제거

        if (!jwtUtil.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        String email = jwtUtil.extractEmail(refreshToken);
        String newAccessToken = jwtUtil.generateAccessToken(email); // ✅ AccessToken만 새로 발급

        return ResponseEntity.ok(newAccessToken);
    }

    // ✅ 마이 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String jwtToken = token.substring(7); // "Bearer " 제거
        String email = jwtUtil.extractEmail(jwtToken); // ✅ JWT에서 이메일 추출

        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UserProfileResponse response = new UserProfileResponse(
                user.getEmail(), user.getNickname(), user.getBirthDate(), user.getGender()
        );

        return ResponseEntity.ok(response);
    }

    // ✅ DTO 클래스
    @Getter
    @Setter
    public static class RegisterRequest {
        private String email;
        private String password;
        private String confirmPassword; // ✅ 추가
        private String birthYear; // ✅ 출생년도 (문자열)
        private String gender;
    }

    @Getter
    @Setter
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class SocialLoginRequest {
        private String provider;
        private String accessToken;
    }

    @Getter
    @Setter
    public static class UserProfileResponse {
        private String email;
        private String nickname;
        private String birthDate;
        private String gender;

        public UserProfileResponse(String email, String nickname, String birthDate, String gender) {
            this.email = email;
            this.nickname = nickname;
            this.birthDate = birthDate;
            this.gender = gender;
        }
    }
}
