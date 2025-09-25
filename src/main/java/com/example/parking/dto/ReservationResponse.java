package com.example.parking.dto;

import com.example.parking.entity.ReservationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationResponse {
    private Long id;
    private String vehicleNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalCost;
    private ReservationStatus status;
    private SlotResponse slot;
    private LocalDateTime createdAt;
}