package com.example.travel.repository;

import com.example.travel.domain.TravelPlanDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelPlanDayRepository extends JpaRepository<TravelPlanDay, Long> {
}
