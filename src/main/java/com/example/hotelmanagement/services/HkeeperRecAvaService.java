package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Housekeeping;
import com.example.hotelmanagement.repository.HousekeepingRepository;

import java.sql.SQLException;
import java.util.List;

public class HkeeperRecAvaService {
    private final HousekeepingRepository housekeepingRepository;

    public HkeeperRecAvaService(HousekeepingRepository hkeeperRecAvaRepository) {
        this.housekeepingRepository = hkeeperRecAvaRepository;
    }

    public void viewAllHousekeepersWithAvailability() throws SQLException {
        List<Housekeeping> housekeepers = housekeepingRepository.getAllHousekeepersWithAvailability();

        if (housekeepers.isEmpty()) {
            System.out.println("No housekeepers available.");
        } else {
            System.out.println("Housekeepers and their availability:");
            for (Housekeeping housekeeper : housekeepers) {
                System.out.println(housekeeper);
            }
        }
    }
}