package com.example.travel.service;

import com.example.travel.domain.User;
import com.example.travel.repository.UserRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, StringRedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    // 회원가입
    public void register(String email, String password, String nickname) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname);
        userRepository.save(user);
    }

    // 로그인
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String accessToken = jwtUtil.generateToken(email);
        String refreshToken = jwtUtil.generateToken(email);

        return "Access Token: " + accessToken + ", Refresh Token: " + refreshToken;
    }


    // 소셜 로그인
    public String socialLogin(String provider, String accessToken) {
        // 실제 소셜 인증 로직은 생략 (더미 데이터로 처리)
        User user = userRepository.findByEmail(provider + "_user@example.com")
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(provider + "_user@example.com");
                    newUser.setPassword(""); // 소셜 로그인은 비밀번호 없음
                    return userRepository.save(newUser);
                });
        return jwtUtil.generateToken(user.getEmail());
    }


    public void logout(String token) {
        String email = jwtUtil.extractEmail(token); // 🛠 JwtUtil에서 메서드 호출하도록 변경
        redisTemplate.delete(email); // 🛠 redisTemplate 주입받아 사용
    }



    // 토큰을 기반으로 사용자 조회
    public User getUserByToken(String token) {
        // 실제로는 토큰 디코딩하여 사용자 ID 추출
        // 여기서는 더미로 첫 번째 사용자 반환
        return userRepository.findById(1L).orElse(null);
    }
}
