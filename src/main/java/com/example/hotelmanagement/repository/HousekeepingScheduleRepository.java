package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.HousekeepingSchedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HousekeepingScheduleRepository {
    private Connection connection;

    // Constructor to initialize the database connection
    public HousekeepingScheduleRepository(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new housekeeping schedule
    public void addHousekeepingTask(HousekeepingSchedule schedule) throws SQLException {
        String sql = "INSERT INTO Housekeeping_Schedule (TaskDate, Status, Housekeeping_ID, Room_ID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, schedule.getTaskDate());
            stmt.setString(2, schedule.getStatus());
            stmt.setInt(3, schedule.getHousekeepingId());
            stmt.setInt(4, schedule.getRoomId());
            stmt.executeUpdate();
        }
    }

    // Retrieve all housekeeping schedules
    public List<HousekeepingSchedule> getAllSchedules() throws SQLException {
        String sql = "SELECT * FROM Housekeeping_Schedule";
        List<HousekeepingSchedule> schedules = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                schedules.add(new HousekeepingSchedule(
                        rs.getInt("TaskID"),
                        rs.getDate("TaskDate"),
                        rs.getString("Status"),
                        rs.getInt("Housekeeping_ID"),
                        rs.getInt("Room_ID")
                ));
            }
        }
        return schedules;
    }
}
