package com.example.parking.service;

import com.example.parking.dto.SlotRequest;
import com.example.parking.dto.SlotResponse;
import com.example.parking.dto.AvailabilityRequest;
import com.example.parking.entity.Floor;
import com.example.parking.entity.Slot;
import com.example.parking.exception.ResourceAlreadyExistsException;
import com.example.parking.exception.ResourceNotFoundException;
import com.example.parking.repository.FloorRepository;
import com.example.parking.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SlotService {
    
    private final SlotRepository slotRepository;
    private final FloorRepository floorRepository;
    
    public SlotResponse createSlot(SlotRequest request) {
        Floor floor = floorRepository.findById(request.getFloorId())
                .orElseThrow(() -> new ResourceNotFoundException("Floor not found with id: " + request.getFloorId()));
        
        if (slotRepository.findBySlotNumberAndFloorId(request.getSlotNumber(), request.getFloorId()).isPresent()) {
            throw new ResourceAlreadyExistsException("Slot " + request.getSlotNumber() + " already exists on floor " + request.getFloorId());
        }
        
        Slot slot = new Slot();
        slot.setSlotNumber(request.getSlotNumber());
        slot.setVehicleType(request.getVehicleType());
        slot.setFloor(floor);
        
        Slot savedSlot = slotRepository.save(slot);
        return mapToResponse(savedSlot);
    }
    
    @Transactional(readOnly = true)
    @Cacheable(value = "availableSlots", key = "#request.startTime + '_' + #request.endTime + '_' + #request.floorId")
    public List<SlotResponse> getAvailableSlots(AvailabilityRequest request) {
        List<Slot> availableSlots;
        
        if (request.getFloorId() != null) {
            availableSlots = slotRepository.findAvailableSlotsByFloor(
                    request.getFloorId(), request.getStartTime(), request.getEndTime());
        } else {
            availableSlots = slotRepository.findAvailableSlots(
                    request.getStartTime(), request.getEndTime());
        }
        
        return availableSlots.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<SlotResponse> getAllSlots() {
        return slotRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    private SlotResponse mapToResponse(Slot slot) {
        SlotResponse response = new SlotResponse();
        response.setId(slot.getId());
        response.setSlotNumber(slot.getSlotNumber());
        response.setVehicleType(slot.getVehicleType());
        response.setFloorId(slot.getFloor().getId());
        response.setFloorName(slot.getFloor().getName());
        return response;
    }
}