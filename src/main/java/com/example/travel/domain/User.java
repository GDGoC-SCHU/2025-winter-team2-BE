package com.example.travel.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TravelPlan> travelPlans;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommunityPost> communityPosts;
}
