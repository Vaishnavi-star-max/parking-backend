package com.example.parking.controller;

import com.example.parking.dto.ReservationRequest;
import com.example.parking.dto.ReservationResponse;
import com.example.parking.dto.AvailabilityRequest;
import com.example.parking.dto.SlotResponse;
import com.example.parking.service.ReservationService;
import com.example.parking.service.SlotService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class ReservationController {
    
    private final ReservationService reservationService;
    private final SlotService slotService;
    
    @PostMapping("/reserve")
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        ReservationResponse response = reservationService.createReservation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long id) {
        ReservationResponse response = reservationService.getReservation(id);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/availability")
    public ResponseEntity<List<SlotResponse>> getAvailableSlots(@Valid @RequestBody AvailabilityRequest request) {
        List<SlotResponse> availableSlots = slotService.getAvailableSlots(request);
        return ResponseEntity.ok(availableSlots);
    }
    
    @GetMapping("/availability")
    public ResponseEntity<List<SlotResponse>> getAvailableSlotsWithParams(
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam(required = false) Long floorId) {
        
        AvailabilityRequest request = new AvailabilityRequest();
        request.setStartTime(java.time.LocalDateTime.parse(startTime));
        request.setEndTime(java.time.LocalDateTime.parse(endTime));
        request.setFloorId(floorId);
        
        List<SlotResponse> availableSlots = slotService.getAvailableSlots(request);
        return ResponseEntity.ok(availableSlots);
    }
}