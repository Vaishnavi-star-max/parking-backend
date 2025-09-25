package com.example.parking.dto;

import lombok.Data;

@Data
public class FloorResponse {
    private Long id;
    private String name;
    private Integer floorNumber;
    private Integer totalSlots;
}