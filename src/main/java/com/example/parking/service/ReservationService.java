package com.example.parking.service;

import com.example.parking.dto.ReservationRequest;
import com.example.parking.dto.ReservationResponse;
import com.example.parking.dto.SlotResponse;
import com.example.parking.entity.Reservation;
import com.example.parking.entity.ReservationStatus;
import com.example.parking.entity.Slot;
import com.example.parking.exception.InvalidReservationException;
import com.example.parking.exception.ResourceNotFoundException;
import com.example.parking.exception.SlotNotAvailableException;
import com.example.parking.repository.ReservationRepository;
import com.example.parking.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    
    private final ReservationRepository reservationRepository;
    private final SlotRepository slotRepository;
    private final UserService userService;
    
    public ReservationResponse createReservation(ReservationRequest request) {
        validateReservationRequest(request);
        
        Slot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + request.getSlotId()));
        
        // Check for conflicts
        List<Reservation> conflicts = reservationRepository.findConflictingReservations(
                request.getSlotId(), request.getStartTime(), request.getEndTime());
        
        if (!conflicts.isEmpty()) {
            throw new SlotNotAvailableException("Slot is not available for the requested time period");
        }
        
        // Calculate cost
        double totalCost = calculateCost(slot, request.getStartTime(), request.getEndTime());
        
        Reservation reservation = new Reservation();
        reservation.setSlot(slot);
        reservation.setUser(userService.getCurrentUser());
        reservation.setVehicleNumber(request.getVehicleNumber());
        reservation.setStartTime(request.getStartTime());
        reservation.setEndTime(request.getEndTime());
        reservation.setTotalCost(totalCost);
        reservation.setStatus(ReservationStatus.ACTIVE);
        
        Reservation savedReservation = reservationRepository.save(reservation);
        return mapToResponse(savedReservation);
    }
    
    @Transactional(readOnly = true)
    @Cacheable(value = "reservations", key = "#id")
    @PreAuthorize("@reservationService.isReservationOwner(#id) or hasRole('ADMIN')")
    public ReservationResponse getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        
        return mapToResponse(reservation);
    }
    
    public boolean isReservationOwner(Long reservationId) {
        try {
            Reservation reservation = reservationRepository.findById(reservationId)
                    .orElse(null);
            if (reservation == null) return false;
            
            return reservation.getUser().getEmail().equals(userService.getCurrentUser().getEmail());
        } catch (Exception e) {
            return false;
        }
    }
    
    @PreAuthorize("@reservationService.isReservationOwner(#id) or hasRole('ADMIN')")
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new InvalidReservationException("Reservation is already cancelled");
        }
        
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }
    
    private void validateReservationRequest(ReservationRequest request) {
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new InvalidReservationException("Start time must be before end time");
        }
        
        if (request.getStartTime().isBefore(LocalDateTime.now())) {
            throw new InvalidReservationException("Start time cannot be in the past");
        }
        
        Duration duration = Duration.between(request.getStartTime(), request.getEndTime());
        if (duration.toHours() > 24) {
            throw new InvalidReservationException("Reservation duration cannot exceed 24 hours");
        }
    }
    
    private double calculateCost(Slot slot, LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        long hours = duration.toHours();
        
        // Round up partial hours
        if (duration.toMinutes() % 60 > 0) {
            hours++;
        }
        
        return hours * slot.getVehicleType().getHourlyRate();
    }
    
    private ReservationResponse mapToResponse(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setVehicleNumber(reservation.getVehicleNumber());
        response.setStartTime(reservation.getStartTime());
        response.setEndTime(reservation.getEndTime());
        response.setTotalCost(reservation.getTotalCost());
        response.setStatus(reservation.getStatus());
        response.setCreatedAt(reservation.getCreatedAt());
        
        // Map slot
        SlotResponse slotResponse = new SlotResponse();
        slotResponse.setId(reservation.getSlot().getId());
        slotResponse.setSlotNumber(reservation.getSlot().getSlotNumber());
        slotResponse.setVehicleType(reservation.getSlot().getVehicleType());
        slotResponse.setFloorId(reservation.getSlot().getFloor().getId());
        slotResponse.setFloorName(reservation.getSlot().getFloor().getName());
        response.setSlot(slotResponse);
        
        return response;
    }
}