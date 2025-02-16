package com.example.travel.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;
    private String nickname;
    private String socialType; // 소셜 로그인 타입 (e.g., "NAVER", "KAKAO")
    private String socialId;   // 소셜 계정 ID
    private String birthDate; // ✅ 생년월일 추가 (YYYY.MM.DD 형식)
    private String gender; // ✅ 성별 추가 ("M" = 남성, "F" = 여성, "O" = 기타)

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TravelPlan> travelPlans;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommunityPost> communityPosts;
}
