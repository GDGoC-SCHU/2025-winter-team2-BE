package com.example.travel.controller;

import com.example.travel.domain.PlanSpot;
import com.example.travel.domain.TravelPlan;
import com.example.travel.service.AiService;
import com.example.travel.service.RouteService;
import com.example.travel.service.TravelService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


// AI 추천 요청 API
@RestController
@RequestMapping("/api/ai")
public class AiController {


    private final RestTemplate restTemplate = new RestTemplate();
    private final String AI_SERVER_URL = "http://localhost:5000";  // AI 서버 주소

    @GetMapping("/health-check")
    public ResponseEntity<String> checkAIServer() {
        String url = AI_SERVER_URL + "/ai-health"; // AI 서버의 health check 엔드포인트
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return ResponseEntity.ok("✅ AI 서버 응답: " + response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ AI 서버 연결 실패: " + e.getMessage());
        }
    }

    @Getter
    @Setter
    public class AiRequest {
        private String location;
        private String purpose;
        private String category;
        private int dayCount;
    }
}

