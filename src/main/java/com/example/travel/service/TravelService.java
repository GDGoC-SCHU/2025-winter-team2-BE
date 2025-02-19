package com.example.travel.service;

import com.example.travel.controller.TravelController;
import com.example.travel.domain.PlanSpot;
import com.example.travel.domain.TravelPlan;
import com.example.travel.domain.TravelPlanDay;
import com.example.travel.domain.User;
import com.example.travel.repository.PlanSpotRepository;
import com.example.travel.repository.TravelPlanDayRepository;
import com.example.travel.repository.TravelPlanRepository;
import com.example.travel.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelPlanRepository travelPlanRepository;
    private final UserRepository userRepository;

    @Transactional
    public TravelPlan saveTravelPlan(Map<String, Object> aiResponse, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        TravelPlan travelPlan = new TravelPlan();
        travelPlan.setLocation((String) aiResponse.get("location"));
        travelPlan.setDayCount((Integer) aiResponse.get("dayCount"));
        travelPlan.setCategory((String) aiResponse.get("category"));
        travelPlan.setUser(user);

        List<Map<String, Object>> travelPlanDays = (List<Map<String, Object>>) aiResponse.get("travelPlanDays");
        List<TravelPlanDay> travelPlanDayList = new ArrayList<>();

        for (Map<String, Object> dayData : travelPlanDays) {
            TravelPlanDay travelPlanDay = new TravelPlanDay();
            travelPlanDay.setDayIndex((Integer) dayData.get("dayIndex"));
            travelPlanDay.setTravelPlan(travelPlan);

            List<Map<String, Object>> spots = (List<Map<String, Object>>) dayData.get("planSpots");
            List<PlanSpot> planSpotList = new ArrayList<>();

            for (Map<String, Object> spotData : spots) {
                PlanSpot spot = new PlanSpot();
                spot.setName((String) spotData.get("name"));
                spot.setCategory((String) spotData.get("category"));
                spot.setLocation((String) spotData.get("location"));
                spot.setTravelPlanDay(travelPlanDay);
                planSpotList.add(spot);
            }

            travelPlanDay.setPlanSpots(planSpotList);
            travelPlanDayList.add(travelPlanDay);
        }

        travelPlan.setTravelPlanDays(travelPlanDayList);
        return travelPlanRepository.save(travelPlan);
    }

    public TravelPlan getLatestTravelPlanByUser(Long userId) {
        return travelPlanRepository.findTopByUserIdOrderByIdDesc(userId)
                .orElseThrow(() -> new IllegalArgumentException("여행 일정이 없습니다."));
    }

}