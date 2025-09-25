package com.example.parking.repository;

import com.example.parking.entity.Slot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    
    Optional<Slot> findBySlotNumberAndFloorId(String slotNumber, Long floorId);
    
    List<Slot> findByFloorId(Long floorId);
    
    @Query("SELECT s FROM Slot s WHERE s.id NOT IN " +
           "(SELECT r.slot.id FROM Reservation r WHERE r.status = 'ACTIVE' AND " +
           "((r.startTime <= :endTime AND r.endTime >= :startTime)))")
    List<Slot> findAvailableSlots(@Param("startTime") LocalDateTime startTime, 
                                  @Param("endTime") LocalDateTime endTime);
    
    @Query("SELECT s FROM Slot s WHERE s.floor.id = :floorId AND s.id NOT IN " +
           "(SELECT r.slot.id FROM Reservation r WHERE r.status = 'ACTIVE' AND " +
           "((r.startTime <= :endTime AND r.endTime >= :startTime)))")
    List<Slot> findAvailableSlotsByFloor(@Param("floorId") Long floorId,
                                         @Param("startTime") LocalDateTime startTime, 
                                         @Param("endTime") LocalDateTime endTime);
}