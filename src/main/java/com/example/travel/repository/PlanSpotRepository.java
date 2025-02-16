package com.example.travel.repository;

import com.example.travel.domain.PlanSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanSpotRepository extends JpaRepository<PlanSpot, Long> {
    List<PlanSpot> findByTravelPlanDayId(Long travelPlanDayId);
}
