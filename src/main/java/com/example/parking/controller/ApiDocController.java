package com.example.parking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiDocController {
    
    @GetMapping("/docs")
    public ResponseEntity<Map<String, Object>> getApiDocumentation() {
        Map<String, Object> docs = new HashMap<>();
        
        docs.put("title", "🅿️ Parking Lot Management API");
        docs.put("version", "1.0.0");
        docs.put("description", "Complete REST API for managing parking lot reservations, floors, and slots with JWT authentication");
        docs.put("baseUrl", "http://localhost:8080");
        
        // Authentication info
        Map<String, Object> auth = new HashMap<>();
        auth.put("type", "Bearer Token (JWT)");
        auth.put("header", "Authorization: Bearer {token}");
        auth.put("note", "Get token from /api/auth/login endpoint");
        docs.put("authentication", auth);
        
        // Business rules
        Map<String, Object> rules = new HashMap<>();
        rules.put("vehicleTypes", Map.of(
            "FOUR_WHEELER", "₹30 per hour",
            "TWO_WHEELER", "₹20 per hour"
        ));
        rules.put("reservationRules", Map.of(
            "maxDuration", "24 hours",
            "billing", "Partial hours rounded up (1.2 hours = 2 hours)",
            "vehicleNumberFormat", "XX00XX0000 (e.g., KA05MH1234)"
        ));
        docs.put("businessRules", rules);
        
        // Detailed endpoints
        Map<String, Object> endpoints = new HashMap<>();
        
        // Authentication
        Map<String, Object> authEndpoints = new HashMap<>();
        authEndpoints.put("signup", Map.of(
            "method", "POST",
            "url", "/api/auth/signup",
            "description", "Register a new user",
            "auth", "None required",
            "example", Map.of(
                "firstName", "John",
                "lastName", "Doe",
                "email", "john@example.com",
                "password", "Password123!",
                "phoneNumber", "9876543210"
            )
        ));
        authEndpoints.put("login", Map.of(
            "method", "POST",
            "url", "/api/auth/login",
            "description", "Login and get JWT token",
            "auth", "None required",
            "example", Map.of(
                "email", "john@example.com",
                "password", "Password123!"
            )
        ));
        endpoints.put("🔐 Authentication", authEndpoints);
        
        // Floors
        Map<String, Object> floorEndpoints = new HashMap<>();
        floorEndpoints.put("createFloor", Map.of(
            "method", "POST",
            "url", "/api/floors",
            "description", "Create a parking floor",
            "auth", "Required",
            "example", Map.of(
                "name", "Ground Floor",
                "floorNumber", 0
            )
        ));
        floorEndpoints.put("getAllFloors", Map.of(
            "method", "GET",
            "url", "/api/floors",
            "description", "Get all floors",
            "auth", "Required"
        ));
        endpoints.put("🏢 Floors", floorEndpoints);
        
        // Slots
        Map<String, Object> slotEndpoints = new HashMap<>();
        slotEndpoints.put("createSlot", Map.of(
            "method", "POST",
            "url", "/api/slots",
            "description", "Create parking slots for a floor",
            "auth", "Required",
            "example", Map.of(
                "slotNumber", "G001",
                "vehicleType", "FOUR_WHEELER",
                "floorId", 1
            )
        ));
        slotEndpoints.put("getAllSlots", Map.of(
            "method", "GET",
            "url", "/api/slots",
            "description", "Get all slots",
            "auth", "Required"
        ));
        endpoints.put("🅿️ Slots", slotEndpoints);
        
        // Reservations
        Map<String, Object> reservationEndpoints = new HashMap<>();
        reservationEndpoints.put("reserve", Map.of(
            "method", "POST",
            "url", "/api/reserve",
            "description", "Reserve a slot with cost calculation",
            "auth", "Required",
            "example", Map.of(
                "slotId", 1,
                "vehicleNumber", "KA05MH1234",
                "startTime", "2025-09-25T10:00:00",
                "endTime", "2025-09-25T12:00:00"
            )
        ));
        reservationEndpoints.put("getReservation", Map.of(
            "method", "GET",
            "url", "/api/reservations/{id}",
            "description", "Get reservation details",
            "auth", "Required"
        ));
        reservationEndpoints.put("cancelReservation", Map.of(
            "method", "DELETE",
            "url", "/api/reservations/{id}",
            "description", "Cancel a reservation",
            "auth", "Required"
        ));
        endpoints.put("📝 Reservations", reservationEndpoints);
        
        // Availability
        Map<String, Object> availabilityEndpoints = new HashMap<>();
        availabilityEndpoints.put("getAvailableSlots", Map.of(
            "method", "GET",
            "url", "/api/availability",
            "description", "Get available slots using query parameters",
            "auth", "Required",
            "queryParams", "?startTime=2025-09-25T10:00:00&endTime=2025-09-25T12:00:00&floorId=1"
        ));
        availabilityEndpoints.put("getAvailableSlotsPost", Map.of(
            "method", "POST",
            "url", "/api/availability",
            "description", "Get available slots using request body",
            "auth", "Required",
            "example", Map.of(
                "startTime", "2025-09-25T10:00:00",
                "endTime", "2025-09-25T12:00:00",
                "floorId", 1
            )
        ));
        endpoints.put("🔍 Availability", availabilityEndpoints);
        
        docs.put("endpoints", endpoints);
        
        // Testing workflow
        Map<String, Object> workflow = new HashMap<>();
        workflow.put("step1", "Register user: POST /api/auth/signup");
        workflow.put("step2", "Login: POST /api/auth/login (save the token)");
        workflow.put("step3", "Create floor: POST /api/floors");
        workflow.put("step4", "Create slots: POST /api/slots");
        workflow.put("step5", "Check availability: GET /api/availability");
        workflow.put("step6", "Make reservation: POST /api/reserve");
        docs.put("testingWorkflow", workflow);
        
        return ResponseEntity.ok(docs);
    }
}