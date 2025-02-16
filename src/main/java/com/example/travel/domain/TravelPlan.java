package com.example.travel.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class TravelPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String location;  // 여행지
    private String purpose;   // 여행 목적
    private String category;  // 선호 카테고리
    private int totalBudget;  // 총 예산
    private int dayCount;     // 총 여행 기간(일)


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "travelPlan", cascade = CascadeType.ALL)
    private List<TravelPlanDay> travelPlanDays;

}
