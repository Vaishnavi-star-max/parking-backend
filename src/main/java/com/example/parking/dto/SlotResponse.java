package com.example.parking.dto;

import com.example.parking.entity.VehicleType;
import lombok.Data;

@Data
public class SlotResponse {
    private Long id;
    private String slotNumber;
    private VehicleType vehicleType;
    private Long floorId;
    private String floorName;
}