package com.example.travel.controller;

import com.example.travel.domain.PlanSpot;
import com.example.travel.domain.TravelPlan;
import com.example.travel.service.AIService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/recommend")
    public ResponseEntity<List<PlanSpot>> getRecommendations(@RequestBody TravelPlan travelPlan) {
        List<PlanSpot> recommendations = aiService.getRecommendations(travelPlan);
        return ResponseEntity.ok(recommendations);
    }

    @Getter
    @Setter
    public class AIRequest {
        private String location;
        private String purpose;
        private String category;
        private int dayCount;
    }

}

