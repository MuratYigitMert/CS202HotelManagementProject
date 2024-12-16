package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Room;
import com.example.hotelmanagement.repository.RoomRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AdministratorServiceManageRoomStatus {

    private final RoomRepository roomRepository;

    // Constructor
    public AdministratorServiceManageRoomStatus(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Update room status
    public void updateRoomStatus(int roomId, String newStatus) throws SQLException {
        Room room = roomRepository.findById(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room with ID " + roomId + " does not exist.");
        }
        room.setAvailabilityStatus(newStatus);
        roomRepository.updateRoomStatus(room);
    }

    // Check room availability for new booking
    public boolean checkRoomAvailability(int roomId, LocalDate startDate, LocalDate endDate) throws SQLException {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Room with ID " + roomId + " does not exist.");
        }
        return roomRepository.isRoomAvailable(roomId, startDate, endDate);
    }

    // Check room availability for modification of an existing booking
    public boolean checkRoomAvailabilityForModification(int roomId, int bookingId, LocalDate startDate, LocalDate endDate) throws SQLException {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Room with ID " + roomId + " does not exist.");
        }
        return roomRepository.isRoomAvailableForModification(roomId, bookingId, startDate, endDate);
    }

    // List all available rooms for a given date range
    public List<Integer> listAvailableRooms(LocalDate startDate, LocalDate endDate) throws SQLException {
        return roomRepository.getAvailableRooms(startDate, endDate);
    }

    // Retrieve all rooms
    public List<Room> getAllRooms() throws SQLException {
        return roomRepository.getAllRooms();
    }

    // Retrieve all available rooms
    public List<Room> getAllAvailableRooms() throws SQLException {
        return roomRepository.getAllAvailableRooms();
    }
}
