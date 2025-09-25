package com.example.parking.repository;

import com.example.parking.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
    
    Optional<Floor> findByFloorNumber(Integer floorNumber);
    
    boolean existsByFloorNumber(Integer floorNumber);
}