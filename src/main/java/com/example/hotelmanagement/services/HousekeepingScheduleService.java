package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.HousekeepingSchedule;
import com.example.hotelmanagement.repository.HousekeepingScheduleRepository;

import java.sql.SQLException;

public class HousekeepingScheduleService {
    private final HousekeepingScheduleRepository repository;

    // Constructor
    public HousekeepingScheduleService(HousekeepingScheduleRepository repository) {
        this.repository = repository;
    }

    // Assign a new housekeeping task
    public void assignHousekeepingTask(HousekeepingSchedule schedule) throws SQLException {
        repository.addHousekeepingTask(schedule);
        System.out.println("Housekeeping task assigned successfully!");
    }
}
