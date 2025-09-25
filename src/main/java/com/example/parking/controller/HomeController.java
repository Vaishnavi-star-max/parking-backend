package com.example.parking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        
        response.put("status", "Server is running");
        response.put("service", "Parking Lot Reservation System");
        response.put("version", "1.0.0");
        response.put("timestamp", LocalDateTime.now());
        
        // Navigation links
        Map<String, String> links = new HashMap<>();
        links.put("swagger", "/swagger");
        links.put("api_docs", "/api/docs");
        links.put("auth_signup", "/api/auth/signup");
        links.put("auth_login", "/api/auth/login");
        
        response.put("links", links);
        
        // Quick start info
        Map<String, Object> quickStart = new HashMap<>();
        quickStart.put("description", "Welcome to Parking Lot API");
        quickStart.put("documentation", "Visit /swagger for interactive API documentation");
        quickStart.put("authentication", "Use /api/auth/signup to create account, then /api/auth/login to get JWT token");
        
        response.put("quick_start", quickStart);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }
}