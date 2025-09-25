package com.example.parking.controller;

import com.example.parking.dto.AuthResponse;
import com.example.parking.dto.LoginRequest;
import com.example.parking.dto.SignupRequest;
import com.example.parking.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
    
    private final UserService userService;
    
    @PostMapping("/signup")

    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        AuthResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")

    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}