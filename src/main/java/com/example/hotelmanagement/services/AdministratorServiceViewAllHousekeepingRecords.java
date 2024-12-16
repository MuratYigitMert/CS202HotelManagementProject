package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Housekeeping;
import com.example.hotelmanagement.repository.HousekeepingRepository;

import java.sql.SQLException;
import java.util.List;

public class AdministratorServiceViewAllHousekeepingRecords  {

    private final HousekeepingRepository housekeepingRepository;

    // Constructor
    public AdministratorServiceViewAllHousekeepingRecords (HousekeepingRepository housekeepingRepository) {
        this.housekeepingRepository = housekeepingRepository;
    }

    // View all housekeeping records
    public List<Housekeeping> viewAllHousekeepingRecords() throws SQLException {
        // Fetch all records from the repository
        return housekeepingRepository.getAllHousekeepers();
    }
}

