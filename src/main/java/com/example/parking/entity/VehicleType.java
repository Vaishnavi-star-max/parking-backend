package com.example.parking.entity;

public enum VehicleType {
    TWO_WHEELER(20.0),
    FOUR_WHEELER(30.0);
    
    private final double hourlyRate;
    
    VehicleType(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    
    public double getHourlyRate() {
        return hourlyRate;
    }
}