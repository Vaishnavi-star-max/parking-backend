package com.example.parking.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "floors", indexes = {
    @Index(name = "idx_floor_number", columnList = "floorNumber", unique = true)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Floor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Integer floorNumber;
    
    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Slot> slots;
}