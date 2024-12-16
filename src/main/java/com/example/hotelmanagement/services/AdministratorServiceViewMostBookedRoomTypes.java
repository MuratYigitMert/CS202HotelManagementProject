package com.example.hotelmanagement.services;

import com.example.hotelmanagement.repository.RoomRepository;

import java.sql.SQLException;
import java.util.List;

    public class AdministratorServiceViewMostBookedRoomTypes {

        private final RoomRepository roomRepository;

        public AdministratorServiceViewMostBookedRoomTypes(RoomRepository roomRepository) {
            this.roomRepository = roomRepository;
        }

        /**
         * View the most booked room types.
         *
         * @return A list of room types and their booking counts.
         */
        public List<String> viewMostBookedRoomTypes() {
            try {
                return roomRepository.getMostBookedRoomTypes();
            } catch (SQLException e) {
                System.err.println("Error fetching most booked room types: " + e.getMessage());
                return List.of("Error fetching data");
            }
        }
    }


