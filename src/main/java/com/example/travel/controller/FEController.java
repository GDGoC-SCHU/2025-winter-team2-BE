package com.example.travel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FEController {

    // FE 연결 테스트용 엔드포인트
    @GetMapping("/test")
    public ResponseEntity<String> testConnection() {
        return ResponseEntity.ok("✅ FE와 BE 연결 성공!");
    }

    // FE에서 사용자 데이터 요청
    @GetMapping("/user/{id}")
    public ResponseEntity<String> getUserInfo(@PathVariable Long id) {
        return ResponseEntity.ok("📌 사용자 ID: " + id);
    }
}
