package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.HousekeepingSchedule;
import com.example.hotelmanagement.repository.HousekeepingRepository;

import java.sql.SQLException;
import java.util.List;

public class HousekeepingServiceViewPendingTasks {
    private final HousekeepingRepository housekeepingRepository;

    public HousekeepingServiceViewPendingTasks(HousekeepingRepository housekeepingRepository) {
        this.housekeepingRepository = housekeepingRepository;
    }

    public List<HousekeepingSchedule> getPendingTasks() throws SQLException {
        return housekeepingRepository.getTasksByStatus("Pending");
    }
}
