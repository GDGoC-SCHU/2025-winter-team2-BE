package com.example.travel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private int dayIndex; // 여행 일차 (예: 1일차, 2일차)

    @ManyToOne
    @JoinColumn(name = "travel_plan_id")
    @JsonIgnore
    private TravelPlan travelPlan;

    @OneToMany(mappedBy = "travelPlanDay", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanSpot> planSpots;
}
