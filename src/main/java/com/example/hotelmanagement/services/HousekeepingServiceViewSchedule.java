package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.HousekeepingSchedule;
import com.example.hotelmanagement.repository.HousekeepingRepository;

import java.sql.SQLException;
import java.util.List;

public class HousekeepingServiceViewSchedule {
    private final HousekeepingRepository housekeepingRepository;

    public HousekeepingServiceViewSchedule(HousekeepingRepository housekeepingRepository) {
        this.housekeepingRepository = housekeepingRepository;
    }

    public List<HousekeepingSchedule> getScheduleByEmployeeId(int housekeepingId) throws SQLException {
        return housekeepingRepository.getTasksByHousekeepingId(housekeepingId);
    }
}
