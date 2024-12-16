package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Room;
import com.example.hotelmanagement.repository.RoomRepository;

public class AdministratorServiceAddRoom {

    private final RoomRepository roomRepository;

    // Constructor for dependency injection
    public AdministratorServiceAddRoom(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Add Room
    public String addRoom(Room room) {
        try {
            // Check if the room already exists
            if (roomRepository.existsById(room.getRoomId())) {
                return "Error: Room with ID " + room.getRoomId() + " already exists.";
            }

            // If the room does not exist, save it to the repository
            roomRepository.insertRoom(room);
            return "Room added successfully!";
        } catch (Exception e) {
            return "Error: Unable to add room. Details: " + e.getMessage();
        }
    }
}


