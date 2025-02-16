package com.example.travel.service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

public class AiService {

    private static final String AI_SERVER_URL = "http://localhost:5000/generate-itinerary";

    public static Map<String, Object> getItinerary(String location, int days, String theme) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("location", location);
        requestBody.put("days", days);
        requestBody.put("theme", theme);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                AI_SERVER_URL, HttpMethod.POST, request, Map.class);

        return response.getBody();
    }
}
