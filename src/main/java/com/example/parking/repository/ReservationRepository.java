package com.example.parking.repository;

import com.example.parking.entity.Reservation;
import com.example.parking.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    List<Reservation> findBySlotIdAndStatus(Long slotId, ReservationStatus status);
    
    @Query("SELECT r FROM Reservation r WHERE r.slot.id = :slotId AND r.status = 'ACTIVE' AND " +
           "((r.startTime <= :endTime AND r.endTime >= :startTime))")
    List<Reservation> findConflictingReservations(@Param("slotId") Long slotId,
                                                  @Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime);
}