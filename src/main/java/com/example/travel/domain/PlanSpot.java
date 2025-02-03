package com.example.travel.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PlanSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String spotName;      // 장소 이름
    private String spotAddress;   // 장소 주소
    private String spotLink;      // 장소 링크
    private int estimatedCost;    // 예상 비용
    private double distance;      // 거리
    private int duration;         // 체류 시간 (분 단위)
    private int spotOrder;        // 방문 순서

    @ManyToOne
    @JoinColumn(name = "travel_plan_day_id")
    private TravelPlanDay travelPlanDay;
}

