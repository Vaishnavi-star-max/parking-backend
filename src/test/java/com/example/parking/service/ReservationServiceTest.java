package com.example.parking.service;

import com.example.parking.dto.ReservationRequest;
import com.example.parking.dto.ReservationResponse;
import com.example.parking.entity.*;
import com.example.parking.exception.InvalidReservationException;
import com.example.parking.exception.ResourceNotFoundException;
import com.example.parking.exception.SlotNotAvailableException;
import com.example.parking.repository.ReservationRepository;
import com.example.parking.repository.SlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    
    @Mock
    private ReservationRepository reservationRepository;
    
    @Mock
    private SlotRepository slotRepository;
    
    @InjectMocks
    private ReservationService reservationService;
    
    private Slot testSlot;
    private Floor testFloor;
    private ReservationRequest validRequest;
    
    @BeforeEach
    void setUp() {
        testFloor = new Floor();
        testFloor.setId(1L);
        testFloor.setName("Ground Floor");
        testFloor.setFloorNumber(1);
        
        testSlot = new Slot();
        testSlot.setId(1L);
        testSlot.setSlotNumber("A1");
        testSlot.setVehicleType(VehicleType.FOUR_WHEELER);
        testSlot.setFloor(testFloor);
        
        validRequest = new ReservationRequest();
        validRequest.setSlotId(1L);
        validRequest.setVehicleNumber("KA05MH1234");
        validRequest.setStartTime(LocalDateTime.now().plusHours(1));
        validRequest.setEndTime(LocalDateTime.now().plusHours(3));
    }
    
    @Test
    void createReservation_Success() {
        // Given
        when(slotRepository.findById(1L)).thenReturn(Optional.of(testSlot));
        when(reservationRepository.findConflictingReservations(anyLong(), any(), any()))
                .thenReturn(Collections.emptyList());
        
        Reservation savedReservation = new Reservation();
        savedReservation.setId(1L);
        savedReservation.setSlot(testSlot);
        savedReservation.setVehicleNumber(validRequest.getVehicleNumber());
        savedReservation.setStartTime(validRequest.getStartTime());
        savedReservation.setEndTime(validRequest.getEndTime());
        savedReservation.setTotalCost(60.0);
        savedReservation.setStatus(ReservationStatus.ACTIVE);
        savedReservation.setCreatedAt(LocalDateTime.now());
        
        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);
        
        // When
        ReservationResponse response = reservationService.createReservation(validRequest);
        
        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("KA05MH1234", response.getVehicleNumber());
        assertEquals(60.0, response.getTotalCost());
        assertEquals(ReservationStatus.ACTIVE, response.getStatus());
        
        verify(slotRepository).findById(1L);
        verify(reservationRepository).findConflictingReservations(anyLong(), any(), any());
        verify(reservationRepository).save(any(Reservation.class));
    }
    
    @Test
    void createReservation_SlotNotFound() {
        // Given
        when(slotRepository.findById(1L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(ResourceNotFoundException.class, 
                () -> reservationService.createReservation(validRequest));
        
        verify(slotRepository).findById(1L);
        verifyNoInteractions(reservationRepository);
    }
    
    @Test
    void createReservation_SlotNotAvailable() {
        // Given
        when(slotRepository.findById(1L)).thenReturn(Optional.of(testSlot));
        
        Reservation conflictingReservation = new Reservation();
        when(reservationRepository.findConflictingReservations(anyLong(), any(), any()))
                .thenReturn(Collections.singletonList(conflictingReservation));
        
        // When & Then
        assertThrows(SlotNotAvailableException.class, 
                () -> reservationService.createReservation(validRequest));
        
        verify(slotRepository).findById(1L);
        verify(reservationRepository).findConflictingReservations(anyLong(), any(), any());
        verify(reservationRepository, never()).save(any());
    }
    
    @Test
    void createReservation_InvalidTimeRange() {
        // Given
        validRequest.setStartTime(LocalDateTime.now().plusHours(3));
        validRequest.setEndTime(LocalDateTime.now().plusHours(1)); // End before start
        
        // When & Then
        assertThrows(InvalidReservationException.class, 
                () -> reservationService.createReservation(validRequest));
        
        verifyNoInteractions(slotRepository);
        verifyNoInteractions(reservationRepository);
    }
    
    @Test
    void createReservation_PastStartTime() {
        // Given
        validRequest.setStartTime(LocalDateTime.now().minusHours(1)); // Past time
        
        // When & Then
        assertThrows(InvalidReservationException.class, 
                () -> reservationService.createReservation(validRequest));
        
        verifyNoInteractions(slotRepository);
        verifyNoInteractions(reservationRepository);
    }
    
    @Test
    void createReservation_ExceedsMaxDuration() {
        // Given
        validRequest.setStartTime(LocalDateTime.now().plusHours(1));
        validRequest.setEndTime(LocalDateTime.now().plusHours(26)); // More than 24 hours
        
        // When & Then
        assertThrows(InvalidReservationException.class, 
                () -> reservationService.createReservation(validRequest));
        
        verifyNoInteractions(slotRepository);
        verifyNoInteractions(reservationRepository);
    }
}