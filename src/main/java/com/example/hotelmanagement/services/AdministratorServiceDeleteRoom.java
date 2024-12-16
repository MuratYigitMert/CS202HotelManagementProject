package com.example.hotelmanagement.services;

import com.example.hotelmanagement.repository.RoomRepository;
import com.example.hotelmanagement.repository.BookingRepository;

import java.sql.SQLException;

public class AdministratorServiceDeleteRoom {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    // Constructor for dependency injection
    public AdministratorServiceDeleteRoom(RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public String deleteRoom(int roomId) {
        try {
            // Step 1: Check if there are any bookings for the room
            if (bookingRepository.hasActiveBookings(roomId)) {
                // If room has active bookings, do not allow deletion
                return "Error: Room has active bookings and cannot be deleted.";
            }

            // Step 2: Optionally, you can mark the room as "inactive" instead of deleting it
            // roomRepository.markRoomAsInactive(roomId);

            // Step 3: Delete the room if no bookings exist or after making it inactive
            roomRepository.deleteRoom(roomId);
            return "Room deleted successfully!";
        } catch (SQLException e) {
            return "Error: Unable to delete room. Details: " + e.getMessage();
        }
    }

}

