package com.example.parking.exception;

public class SlotNotAvailableException extends RuntimeException {
    public SlotNotAvailableException(String message) {
        super(message);
    }
}