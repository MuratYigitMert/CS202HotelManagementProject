package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {
    // CREATE
    final String INSERT_ROOM = "INSERT INTO Room (hotel_id, room_number, R_availability_status, bed_count, max_occupancy, " +
            "room_size, special_amenities, room_type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    // READ
    final String SELECT_ALL_ROOMS = "SELECT r.room_id, r.hotel_id, r.room_number, r.R_availability_status, r.bed_count, " +
            "r.max_occupancy, r.room_size, r.special_amenities, r.room_type_id, rt.type_name, rt.default_price " +
            "FROM Room r JOIN Room_Type rt ON r.room_type_id = rt.room_type_id";

    final String SELECT_ROOM_BY_ID = "SELECT r.room_id, r.hotel_id, r.room_number, r.R_availability_status, r.bed_count, " +
            "r.max_occupancy, r.room_size, r.special_amenities, r.room_type_id, rt.type_name, rt.default_price " +
            "FROM Room r JOIN Room_Type rt ON r.room_type_id = rt.room_type_id WHERE r.room_id = ?";

    final String SELECT_AVAILABLE_ROOMS = "SELECT r.room_id, r.hotel_id, r.room_number, r.R_availability_status, r.bed_count, " +
            "r.max_occupancy, r.room_size, r.special_amenities, r.room_type_id, rt.type_name, rt.default_price " +
            "FROM Room r JOIN Room_Type rt ON r.room_type_id = rt.room_type_id WHERE r.R_availability_status = 'Available'";

    // UPDATE
    final String UPDATE_ROOM = "UPDATE Room SET hotel_id = ?, room_number = ?, R_availability_status = ?, bed_count = ?, " +
            "max_occupancy = ?, room_size = ?, special_amenities = ?, room_type_id = ? WHERE room_id = ?";

    // DELETE
    final String DELETE_ROOM = "DELETE FROM Room WHERE room_id = ?";
    final String DELETE_ALL_ROOMS = "DELETE FROM Room";

    // Database connection
    private final Connection connection;

    // Constructor
    public RoomRepository(Connection connection) {
        this.connection = connection;
    }

    // CREATE: Insert a new room
    public void insertRoom(Room room) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ROOM)) {
            statement.setInt(1, room.getHotelId());
            statement.setString(2, room.getRoomNumber());
            statement.setString(3, room.getAvailabilityStatus().toString());
            statement.setInt(4, room.getBedCount());
            statement.setInt(5, room.getMaxOccupancy());
            statement.setInt(6, room.getRoomSize());
            statement.setString(7, room.getSpecialAmenities());
            statement.setInt(8, room.getRoomTypeId());
            statement.executeUpdate();
        }
    }

    // READ ALL: Get all rooms
    public List<Room> getAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ROOMS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Room room = new Room(
                        resultSet.getInt("room_id"),
                        resultSet.getInt("hotel_id"),
                        resultSet.getString("room_number"),
                        resultSet.getInt("room_type_id"),
                        resultSet.getString("R_availability_status"),
                        resultSet.getInt("bed_count"),
                        resultSet.getInt("max_occupancy"),
                        resultSet.getInt("room_size"),
                        resultSet.getString("special_amenities"),

                        resultSet.getString("type_name"),
                        resultSet.getDouble("default_price")
                );
                rooms.add(room);
            }
        }
        return rooms;
    }

    // READ BY ID: Get a room by its ID
    public Room getRoomById(int roomId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ROOM_BY_ID)) {
            statement.setInt(1, roomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Room(
                            resultSet.getInt("room_id"),
                            resultSet.getInt("hotel_id"),
                            resultSet.getString("room_number"),
                            resultSet.getInt("room_type_id"),
                            resultSet.getString("R_availability_status"),
                            resultSet.getInt("bed_count"),
                            resultSet.getInt("max_occupancy"),
                            resultSet.getInt("room_size"),
                            resultSet.getString("special_amenities"),
                            resultSet.getString("type_name"),
                            resultSet.getDouble("default_price")
                    );
                }
            }
        }
        return null;
    }

    // READ AVAILABLE ROOMS: Get all available rooms
    public List<Room> getAvailableRooms() throws SQLException {
        List<Room> availableRooms = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_AVAILABLE_ROOMS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Room room = new Room(
                        resultSet.getInt("room_id"),
                        resultSet.getInt("hotel_id"),
                        resultSet.getString("room_number"),
                        resultSet.getInt("room_type_id"),
                        resultSet.getString("R_availability_status"),
                        resultSet.getInt("bed_count"),
                        resultSet.getInt("max_occupancy"),
                        resultSet.getInt("room_size"),
                        resultSet.getString("special_amenities"),

                        resultSet.getString("type_name"),
                        resultSet.getDouble("default_price")
                );
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    // UPDATE: Update an existing room
    public void updateRoom(Room room) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ROOM)) {
            statement.setInt(1, room.getHotelId());
            statement.setString(2, room.getRoomNumber());
            statement.setString(3, room.getAvailabilityStatus());
            statement.setInt(4, room.getBedCount());
            statement.setInt(5, room.getMaxOccupancy());
            statement.setInt(6, room.getRoomSize());
            statement.setString(7, room.getSpecialAmenities());
            statement.setInt(8, room.getRoomTypeId());
            statement.setInt(9, room.getRoomId());
            statement.executeUpdate();
        }
    }

    // DELETE: Delete a room by its ID
    public void deleteRoom(int roomId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ROOM)) {
            statement.setInt(1, roomId);
            statement.executeUpdate();
        }
    }

    // DELETE ALL: Delete all rooms
    public void deleteAllRooms() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL_ROOMS)) {
            statement.executeUpdate();
        }
    }
}
