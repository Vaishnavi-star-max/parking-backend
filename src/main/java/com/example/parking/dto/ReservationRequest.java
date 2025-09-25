package com.example.parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequest {
    
    @NotNull(message = "Slot ID is required")
    @Positive(message = "Slot ID must be positive")
    private Long slotId;
    
    @NotBlank(message = "Vehicle number is required")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$", 
             message = "Vehicle number must match format XX00XX0000 (e.g., KA05MH1234)")
    private String vehicleNumber;
    
    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;
    
    @NotNull(message = "End time is required")
    private LocalDateTime endTime;
}