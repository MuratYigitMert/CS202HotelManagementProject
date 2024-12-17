package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.HousekeepingSchedule;
import com.example.hotelmanagement.repository.HousekeepingRepository;

import java.sql.SQLException;
import java.util.List;

public class HousekeepingServiceViewCompletedTasks {
    private final HousekeepingRepository housekeepingRepository;

    public HousekeepingServiceViewCompletedTasks(HousekeepingRepository housekeepingRepository) {
        this.housekeepingRepository = housekeepingRepository;
    }

    public List<HousekeepingSchedule> getCompletedTasks() throws SQLException {
        return housekeepingRepository.getTasksByStatus("Completed");
    }
}
