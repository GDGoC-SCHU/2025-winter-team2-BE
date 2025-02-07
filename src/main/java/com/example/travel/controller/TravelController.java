package com.example.travel.controller;

import com.example.travel.domain.PlanSpot;
import com.example.travel.domain.TravelPlan;
import com.example.travel.service.TravelService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/travel")
public class TravelController {

    private final TravelService travelService;


    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }
    // 사용자 여행 계획 저장
    @PostMapping("/plans")
    public ResponseEntity<String> saveTravelPlan(@RequestBody TravelPlan travelPlanRequest) {
        travelService.saveTravelPlan(travelPlanRequest);
        return ResponseEntity.ok("Travel plan saved successfully.");
    }

    // 사용자 여행 계획 조회 (모든 계획)
    @GetMapping("/plans")
    public ResponseEntity<List<TravelPlan>> getAllTravelPlans() {
        return ResponseEntity.ok(travelService.getAllTravelPlans());
    }

    // 특정 여행 계획 조회
    @GetMapping("/plans/{planId}")
    public ResponseEntity<TravelPlan> getTravelPlan(@PathVariable Long planId) {
        return ResponseEntity.ok(travelService.getTravelPlan(planId));
    }

    // 여행 계획 삭제
    @DeleteMapping("/plans/{planId}")
    public ResponseEntity<String> deleteTravelPlan(@PathVariable Long planId) {
        travelService.deleteTravelPlan(planId);
        return ResponseEntity.ok("Travel plan deleted successfully.");
    }

    // 장소 저장
    @PostMapping("/spots")
    public ResponseEntity<String> savePlanSpot(@RequestParam Long travelPlanDayId, @RequestBody PlanSpot request) {
        travelService.savePlanSpot(travelPlanDayId, request);
        return ResponseEntity.ok("Spot saved successfully.");
    }

    // 특정 날짜의 장소 조회
    @GetMapping("/spots/{travelDayId}")
    public ResponseEntity<List<PlanSpot>> getPlanSpots(@PathVariable Long travelDayId) {
        return ResponseEntity.ok(travelService.getPlanSpots(travelDayId));
    }

    @PostMapping("/plans/{planId}/recommendations")
    public ResponseEntity<String> saveRecommendations(
            @PathVariable Long planId,
            @RequestBody List<PlanSpot> recommendations) {
        travelService.saveRecommendations(planId, recommendations);
        return ResponseEntity.ok("Recommendations saved successfully.");
    }

    @Getter
    @Setter
    public class PlanSpotDTO {
        private String spotName;
        private String spotAddress;
        private String spotLink;
        private int estimatedCost;
        private double distance;
        private int duration;
        private int spotOrder;
    }


}


