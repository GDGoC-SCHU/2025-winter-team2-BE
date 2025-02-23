package com.example.travel.controller;

import com.example.travel.domain.PlanSpot;
import com.example.travel.domain.TravelPlan;
import com.example.travel.domain.User;
import com.example.travel.dto.TravelPlanDto;
import com.example.travel.repository.UserRepository;
import com.example.travel.service.TravelService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TravelController { // =AIController
    private final TravelService travelService;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String AI_API_URL = "http://localhost:8000/generate_itinerary"; // FastAPI 서버 URL

    @PostMapping("/recommend")
    public ResponseEntity<?> generateTravelPlan(@RequestBody TravelPlanDto.TravelPlanRequest request,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        if (request.getLocation() == null || request.getDays() <= 0 || request.getTheme() == null) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다. location, days, theme 값이 필요합니다.");
        }

        // 현재 로그인한 사용자 정보 가져오기
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String aiRequestUrl = AI_API_URL + "?location=" + request.getLocation() +
                "&days=" + request.getDays() +
                "&theme=" + request.getTheme();

        ResponseEntity<Map> aiResponse = restTemplate.getForEntity(aiRequestUrl, Map.class);
        if (aiResponse.getBody() == null) {
            return ResponseEntity.badRequest().body("AI 응답이 없습니다.");
        }

        TravelPlan travelPlan = travelService.saveTravelPlan(aiResponse.getBody(), user.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "여행 일정이 생성되었습니다.",
                "travelPlanId", travelPlan.getId(),
                "redirectUrl", "/plans/" + user.getId()
        ));
    }


    @GetMapping("/plans/{userId}")
    public ResponseEntity<?> getLatestUserTravelPlan(@PathVariable Long userId) {
        TravelPlan latestPlan = travelService.getLatestTravelPlanByUser(userId);
        if (latestPlan == null) {
            return ResponseEntity.status(404).body("해당 사용자의 최근 생성된 여행 일정이 없습니다.");
        }
        return ResponseEntity.ok(latestPlan);
    }



}

