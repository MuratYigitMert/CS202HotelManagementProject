package com.example.hotelmanagement.services;

import com.example.hotelmanagement.repository.RoomRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ViewRoomAvailability {
    private final RoomRepository roomRepository;

    // Constructor for dependency injection
    public ViewRoomAvailability(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public String viewAvailableRooms(String startDateStr, String endDateStr) {
        try {
            // Step 1: Convert input date strings to java.sql.Date
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            // Step 2: Retrieve all rooms from the repository
            List<Integer> availableRoomIds = roomRepository.getAvailableRooms(startDate, endDate);

            // Step 3: If no rooms are available, return a message
            if (availableRoomIds.isEmpty()) {
                return "No rooms available for the selected dates.";
            }

            // Step 4: Return list of available rooms
            StringBuilder result = new StringBuilder("Available Rooms:\n");
            for (int roomId : availableRoomIds) {
                result.append("Room ID: ").append(roomId).append("\n");
            }
            return result.toString();

        } catch (SQLException e) {
            return "Error: Unable to retrieve room availability. Details: " + e.getMessage();
        }
    }
}
