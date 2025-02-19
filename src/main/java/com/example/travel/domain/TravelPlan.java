package com.example.travel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TravelPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private int dayCount;
    private String category;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;  // 여행 계획을 생성한 사용자

    @OneToMany(mappedBy = "travelPlan", cascade = CascadeType.ALL)
    private List<TravelPlanDay> travelPlanDays;
}
