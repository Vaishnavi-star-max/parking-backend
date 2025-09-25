package com.example.parking.dto;

import com.example.parking.entity.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SlotRequest {
    
    @NotBlank(message = "Slot number is required")
    private String slotNumber;
    
    @NotNull(message = "Vehicle type is required")
    private VehicleType vehicleType;
    
    @NotNull(message = "Floor ID is required")
    @Positive(message = "Floor ID must be positive")
    private Long floorId;
}