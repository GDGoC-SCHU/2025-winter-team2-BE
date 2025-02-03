package com.example.travel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendController {

    // 추천받기
    @PostMapping
    public ResponseEntity<String> getRecommendations(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody RecommendationRequest request
    ) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.ok("로그인 없이 추천받기 제한된 정보 제공"); // 제한된 정보
        }

        return ResponseEntity.ok("로그인 후 추천 결과: " + request.destination + " 추천 코스 반환");
    }

    public static class RecommendationRequest {
        public String destination; // 추천받을 장소
        public String purpose;     // 여행 목적
    }

}
