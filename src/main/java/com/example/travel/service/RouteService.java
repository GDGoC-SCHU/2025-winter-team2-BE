package com.example.travel.service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

public class RouteService {

    private static final String AI_SERVER_URL = "http://localhost:5000/get-route";

    public static Map<String, Object> getRoute(String start, String end) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("start_name", start);
        requestBody.put("end_name", end);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                AI_SERVER_URL, HttpMethod.POST, request, Map.class);

        return response.getBody();
    }
}
