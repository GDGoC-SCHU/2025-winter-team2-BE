package com.example.travel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String name;       // 장소 이름
    private String category;   // 장소 카테고리 (힐링, 액티비티 등)
    private String location;   // 네이버 지도 검색 URL

    @ManyToOne
    @JoinColumn(name = "travel_plan_day_id")
    @JsonIgnore
    private TravelPlanDay travelPlanDay;
}
