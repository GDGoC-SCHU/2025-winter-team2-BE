package com.example.travel.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class TravelPlanDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int dayIndex;    // 여행 일차
    private int dailyCost;   // 하루 예상 비용

    @ManyToOne
    @JoinColumn(name = "travel_plan_id")
    private TravelPlan travelPlan;

    @OneToMany(mappedBy = "travelPlanDay", cascade = CascadeType.ALL)
    private List<PlanSpot> planSpots;
}

