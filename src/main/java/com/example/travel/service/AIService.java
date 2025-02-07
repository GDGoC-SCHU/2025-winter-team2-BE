package com.example.travel.service;

import com.example.travel.controller.AIController;
import com.example.travel.domain.PlanSpot;
import com.example.travel.domain.TravelPlan;
import io.lettuce.core.dynamic.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AIService {
    private final RestTemplate restTemplate;
    public AIService() {
        this.restTemplate = new RestTemplate();
    }

    public List<PlanSpot> getRecommendations(TravelPlan travelPlan) {
        // AI 서버 URL (공인 IP와 포트를 포함)
        String aiServerUrl = "http://ai server/api/recommend";

        // 요청에 사용할 JSON 데이터 생성
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("location", travelPlan.getLocation());
        requestData.put("purpose", travelPlan.getPurpose());
        requestData.put("category", travelPlan.getCategory());
        requestData.put("dayCount", travelPlan.getDayCount());
        requestData.put("totalBudget", travelPlan.getTotalBudget());

        // 요청 보내기
        ResponseEntity<List<PlanSpot>> response = restTemplate.postForEntity(
                aiServerUrl,
                requestData,
                (Class<List<PlanSpot>>) (Object) List.class // 응답 형식을 PlanSpot 리스트로 매핑
        );

        return response.getBody();
    }
}

