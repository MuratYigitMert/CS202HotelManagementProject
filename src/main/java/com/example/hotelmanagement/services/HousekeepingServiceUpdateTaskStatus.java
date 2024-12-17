package com.example.hotelmanagement.services;

import com.example.hotelmanagement.repository.HousekeepingRepository;

import java.sql.SQLException;

public class HousekeepingServiceUpdateTaskStatus {
    private final HousekeepingRepository housekeepingRepository;

    public HousekeepingServiceUpdateTaskStatus(HousekeepingRepository housekeepingRepository) {
        this.housekeepingRepository = housekeepingRepository;
    }

    public void markTaskAsCompleted(int taskId) throws SQLException {
        housekeepingRepository.updateTaskStatus(taskId, "Completed");
    }
}
