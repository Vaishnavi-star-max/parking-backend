package com.example.parking.controller;

import com.example.parking.dto.SlotRequest;
import com.example.parking.dto.SlotResponse;
import com.example.parking.service.SlotService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor

public class SlotController {
    
    private final SlotService slotService;
    
    @PostMapping
    public ResponseEntity<SlotResponse> createSlot(@Valid @RequestBody SlotRequest request) {
        SlotResponse response = slotService.createSlot(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<SlotResponse>> getAllSlots() {
        List<SlotResponse> slots = slotService.getAllSlots();
        return ResponseEntity.ok(slots);
    }
}