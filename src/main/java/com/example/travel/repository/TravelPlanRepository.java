package com.example.travel.repository;

import com.example.travel.domain.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {
    Optional<TravelPlan> findTopByUserIdOrderByIdDesc(Long userId);
}
