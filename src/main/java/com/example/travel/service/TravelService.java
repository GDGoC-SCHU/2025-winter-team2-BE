package com.example.travel.service;

import com.example.travel.controller.TravelController;
import com.example.travel.domain.PlanSpot;
import com.example.travel.domain.TravelPlan;
import com.example.travel.domain.TravelPlanDay;
import com.example.travel.repository.PlanSpotRepository;
import com.example.travel.repository.TravelPlanDayRepository;
import com.example.travel.repository.TravelPlanRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelService {

    private final TravelPlanRepository travelPlanRepository;
    private final TravelPlanDayRepository travelPlanDayRepository;
    private final PlanSpotRepository planSpotRepository;

    public TravelService(TravelPlanRepository travelPlanRepository, TravelPlanDayRepository travelPlanDayRepository, PlanSpotRepository planSpotRepository) {
        this.travelPlanRepository = travelPlanRepository;
        this.travelPlanDayRepository = travelPlanDayRepository;
        this.planSpotRepository = planSpotRepository;
    }

    // 여행 계획 저장
    public void saveTravelPlan(TravelPlan travelPlan) {
        travelPlanRepository.save(travelPlan);
    }

    // 모든 여행 계획 조회
    public List<TravelPlan> getAllTravelPlans() {
        return travelPlanRepository.findAll();
    }

    // 특정 여행 계획 조회
    public TravelPlan getTravelPlan(Long planId) {
        return travelPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Travel plan not found for ID: " + planId));
    }

    // 여행 계획 삭제
    public void deleteTravelPlan(Long planId) {
        TravelPlan travelPlan = travelPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Travel plan not found for ID: " + planId));
        travelPlanRepository.delete(travelPlan);
    }

    // 장소 저장
    public void savePlanSpot(Long travelPlanDayId, PlanSpot planSpot) {
        TravelPlanDay travelPlanDay = travelPlanDayRepository.findById(travelPlanDayId)
                .orElseThrow(() -> new IllegalArgumentException("Travel plan day not found for ID: " + travelPlanDayId));
        planSpot.setTravelPlanDay(travelPlanDay);
        planSpotRepository.save(planSpot);
    }

    // 특정 날짜의 장소 조회
    public List<PlanSpot> getPlanSpots(Long travelPlanDayId) {
        return planSpotRepository.findByTravelPlanDayId(travelPlanDayId);
    }


    public void saveRecommendations(Long planId, List<PlanSpot> recommendations) {
        // 여행 계획 조회
        TravelPlan travelPlan = travelPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Travel plan not found"));

        // 여행 일차 생성
        TravelPlanDay travelPlanDay = new TravelPlanDay();
        travelPlanDay.setTravelPlan(travelPlan);
        travelPlanDay.setDayIndex(1); // 기본적으로 첫째 날로 설정
        travelPlanDayRepository.save(travelPlanDay);

        // 장소 저장
        for (PlanSpot spot : recommendations) {
            spot.setTravelPlanDay(travelPlanDay);
            planSpotRepository.save(spot);
        }
    }
}
