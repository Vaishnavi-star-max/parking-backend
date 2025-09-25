package com.example.parking.config;

import com.example.parking.entity.*;
import com.example.parking.repository.FloorRepository;
import com.example.parking.repository.SlotRepository;
import com.example.parking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final FloorRepository floorRepository;
    private final SlotRepository slotRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        createAdminUser();
        createSampleData();
    }
    
    private void createAdminUser() {
        if (!userRepository.existsByEmail("admin@parking.com")) {
            User admin = new User();
            admin.setFirstName("System");
            admin.setLastName("Admin");
            admin.setEmail("admin@parking.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setPhoneNumber("9999999999");
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);
            
            userRepository.save(admin);
            log.info("Default admin user created: admin@parking.com / Admin@123");
        }
        
        // Create a sample customer user
        if (!userRepository.existsByEmail("john.doe@example.com")) {
            User customer = new User();
            customer.setFirstName("John");
            customer.setLastName("Doe");
            customer.setEmail("john.doe@example.com");
            customer.setPassword(passwordEncoder.encode("Password123!"));
            customer.setPhoneNumber("9876543210");
            customer.setRole(Role.CUSTOMER);
            customer.setEnabled(true);
            
            userRepository.save(customer);
            log.info("Default customer user created: john.doe@example.com / Password123!");
        }
    }
    
    private void createSampleData() {
        if (floorRepository.count() == 0) {
            log.info("Creating sample floors and slots...");
            
            // Create floors
            Floor groundFloor = new Floor();
            groundFloor.setName("Ground Floor");
            groundFloor.setFloorNumber(1);
            groundFloor = floorRepository.save(groundFloor);
            
            Floor firstFloor = new Floor();
            firstFloor.setName("First Floor");
            firstFloor.setFloorNumber(2);
            firstFloor = floorRepository.save(firstFloor);
            
            // Create slots for Ground Floor
            createSlot("G001", VehicleType.FOUR_WHEELER, groundFloor);
            createSlot("G002", VehicleType.FOUR_WHEELER, groundFloor);
            createSlot("G003", VehicleType.TWO_WHEELER, groundFloor);
            createSlot("G004", VehicleType.TWO_WHEELER, groundFloor);
            createSlot("G005", VehicleType.FOUR_WHEELER, groundFloor);
            
            // Create slots for First Floor
            createSlot("F101", VehicleType.FOUR_WHEELER, firstFloor);
            createSlot("F102", VehicleType.FOUR_WHEELER, firstFloor);
            createSlot("F103", VehicleType.TWO_WHEELER, firstFloor);
            createSlot("F104", VehicleType.TWO_WHEELER, firstFloor);
            createSlot("F105", VehicleType.FOUR_WHEELER, firstFloor);
            
            log.info("Sample data created: 2 floors, 10 slots");
        }
    }
    
    private void createSlot(String slotNumber, VehicleType vehicleType, Floor floor) {
        Slot slot = new Slot();
        slot.setSlotNumber(slotNumber);
        slot.setVehicleType(vehicleType);
        slot.setFloor(floor);
        slotRepository.save(slot);
    }
}