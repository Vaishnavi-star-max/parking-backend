package com.example.parking.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "slots", indexes = {
    @Index(name = "idx_slot_floor", columnList = "floor_id"),
    @Index(name = "idx_slot_number_floor", columnList = "slotNumber, floor_id", unique = true),
    @Index(name = "idx_slot_vehicle_type", columnList = "vehicleType")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slot {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String slotNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;
    
    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;
    
    @Version
    private Long version; // For optimistic locking
}