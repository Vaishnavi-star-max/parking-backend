package com.example.parking.service;

import com.example.parking.dto.FloorRequest;
import com.example.parking.dto.FloorResponse;
import com.example.parking.entity.Floor;
import com.example.parking.exception.ResourceAlreadyExistsException;
import com.example.parking.repository.FloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FloorService {
    
    private final FloorRepository floorRepository;
    
    public FloorResponse createFloor(FloorRequest request) {
        if (floorRepository.existsByFloorNumber(request.getFloorNumber())) {
            throw new ResourceAlreadyExistsException("Floor with number " + request.getFloorNumber() + " already exists");
        }
        
        Floor floor = new Floor();
        floor.setName(request.getName());
        floor.setFloorNumber(request.getFloorNumber());
        
        Floor savedFloor = floorRepository.save(floor);
        return mapToResponse(savedFloor);
    }
    
    @Transactional(readOnly = true)
    @Cacheable("floors")
    public List<FloorResponse> getAllFloors() {
        return floorRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    private FloorResponse mapToResponse(Floor floor) {
        FloorResponse response = new FloorResponse();
        response.setId(floor.getId());
        response.setName(floor.getName());
        response.setFloorNumber(floor.getFloorNumber());
        // Avoid lazy loading issues for now
        response.setTotalSlots(0);
        return response;
    }
}