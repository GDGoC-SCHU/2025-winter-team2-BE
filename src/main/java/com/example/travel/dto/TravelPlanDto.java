package com.example.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TravelPlanDto {

    // 요청 DTO (Request)
    @Getter
    @Setter
    @AllArgsConstructor
    public static class TravelPlanRequest {
        private String location;
        private int days;
        private String theme;
    }

    // 응답 DTO (Response)
    @Getter
    @Setter
    @AllArgsConstructor
    public static class TravelPlanResponse {
        private Long id;
        private String location;
        private int dayCount;
        private String category;
        private List<TravelPlanDayDto> travelPlanDays;
    }

    // TravelPlanDay DTO (Nested)
    @Getter
    @Setter
    @AllArgsConstructor
    public static class TravelPlanDayDto {
        private int dayIndex;
        private List<PlanSpotDto> planSpots;
    }

    // PlanSpot DTO (Nested)
    @Getter
    @Setter
    @AllArgsConstructor
    public static class PlanSpotDto {
        private String name;
        private String category;
        private String location;
    }
}
