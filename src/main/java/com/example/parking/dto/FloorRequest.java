package com.example.parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FloorRequest {
    
    @NotBlank(message = "Floor name is required")
    private String name;
    
    @NotNull(message = "Floor number is required")
    @Positive(message = "Floor number must be positive")
    private Integer floorNumber;
}