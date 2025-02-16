package com.example.travel.service;

import com.example.travel.domain.User;
import com.example.travel.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입
    public void register(String email, String password, String birthDate, String gender) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // ✅ 비밀번호 암호화
        user.setBirthDate(birthDate); // ✅ 생년월일 저장
        user.setGender(gender); // ✅ 성별 저장

        userRepository.save(user);
    }

    // 로그인 (AccessToken & RefreshToken 발급)
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // ✅ 올바른 메서드 사용 (generateToken → generateAccessToken)
        String accessToken = jwtUtil.generateAccessToken(email);
        String refreshToken = jwtUtil.generateRefreshToken(email);

        return "Access Token: " + accessToken + ", Refresh Token: " + refreshToken;
    }

    // 소셜 로그인
    public String socialLogin(String provider, String accessToken) {
        User user = userRepository.findByEmail(provider + "_user@example.com")
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(provider + "_user@example.com");
                    newUser.setPassword(""); // 소셜 로그인은 비밀번호 없음
                    return userRepository.save(newUser);
                });

        return jwtUtil.generateAccessToken(user.getEmail()); // ✅ 수정됨
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    // 토큰을 기반으로 사용자 조회
    public User getUserByToken(String token) {
        String email = jwtUtil.extractEmail(token);
        return userRepository.findByEmail(email).orElse(null);
    }
}
