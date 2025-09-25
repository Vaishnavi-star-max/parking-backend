package com.example.parking.controller;

import com.example.parking.dto.FloorRequest;
import com.example.parking.dto.FloorResponse;
import com.example.parking.service.FloorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/floors")
@RequiredArgsConstructor

public class FloorController {

    private final FloorService floorService;

    @PostMapping

    public ResponseEntity<FloorResponse> createFloor(@Valid @RequestBody FloorRequest request) {
        FloorResponse response = floorService.createFloor(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping

    public ResponseEntity<List<FloorResponse>> getAllFloors() {
        List<FloorResponse> floors = floorService.getAllFloors();
        return ResponseEntity.ok(floors);
    }
}