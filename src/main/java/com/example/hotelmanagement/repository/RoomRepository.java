package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Room;
import com.example.hotelmanagement.Entity.Room_Type;

import java.sql.*;
import java.time.LocalDate;
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
        // Extract roomTypeId from the Room_Type object
        int roomTypeId = room.getRoomType().getRoomTypeId();

        // Prepare the SQL query with the correct Room_Type ID
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ROOM)) {
            statement.setInt(1, room.getHotelId());
            statement.setString(2, room.getRoomNumber());
            statement.setString(3, room.getAvailabilityStatus());
            statement.setInt(4, room.getBedCount());
            statement.setInt(5, room.getMaxOccupancy());
            statement.setInt(6, room.getRoomSize());
            statement.setString(7, room.getSpecialAmenities());
            statement.setInt(8, roomTypeId);  // Set the roomTypeId from the Room_Type object
            statement.executeUpdate();
        }
    }


    // READ ALL: Get all rooms
    public List<Room> getAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ROOMS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int roomTypeId = resultSet.getInt("room_type_id");
                Room_Type roomType = getRoomTypeById(roomTypeId);  // Fetch the Room_Type object

                Room room = new Room(
                        resultSet.getInt("room_id"),
                        resultSet.getInt("hotel_id"),
                        resultSet.getString("room_number"),
                        roomType,  // Pass the Room_Type object
                        resultSet.getString("R_availability_status"),
                        resultSet.getInt("bed_count"),
                        resultSet.getInt("max_occupancy"),
                        resultSet.getInt("room_size"),
                        resultSet.getString("special_amenities")
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
                    // Retrieve room details
                    int roomTypeId = resultSet.getInt("room_type_id");
                    Room_Type roomType = getRoomTypeById(roomTypeId);  // Fetch the Room_Type object

                    // Create and return the Room object
                    return new Room(
                            resultSet.getInt("room_id"),
                            resultSet.getInt("hotel_id"),
                            resultSet.getString("room_number"),
                            roomType,  // Pass the Room_Type object
                            resultSet.getString("R_availability_status"),
                            resultSet.getInt("bed_count"),
                            resultSet.getInt("max_occupancy"),
                            resultSet.getInt("room_size"),
                            resultSet.getString("special_amenities")
                    );
                }
            }
        }
        return null;
    }

    // Helper method to fetch Room_Type by its ID
    private Room_Type getRoomTypeById(int roomTypeId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM room_type WHERE room_type_id = ?")) {
            statement.setInt(1, roomTypeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Room_Type(
                            resultSet.getInt("room_type_id"),
                            resultSet.getString("type_name"),
                            resultSet.getDouble("default_price")
                    );
                }
            }
        }
        return null;  // Return null if no matching Room_Type is found
    }
    public List<String> getMostBookedRoomTypes() throws SQLException {
        String query = """
                SELECT rt.type_name, COUNT(*) AS booking_count
                FROM booking b
                INNER JOIN room r ON b.room_id = r.room_id
                INNER JOIN room_type rt ON r.room_type_id = rt.room_type_id
                GROUP BY rt.type_name
                ORDER BY booking_count DESC;
                """;

        List<String> mostBookedRoomTypes = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String roomTypeName = resultSet.getString("type_name");
                int bookingCount = resultSet.getInt("booking_count");
                mostBookedRoomTypes.add(roomTypeName + " - Booked: " + bookingCount + " times");
            }
        }

        return mostBookedRoomTypes;
    }


    // READ AVAILABLE ROOMS: Get all available rooms
    public List<Room> getAllAvailableRooms() throws SQLException {
        List<Room> availableRooms = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_AVAILABLE_ROOMS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int roomTypeId = resultSet.getInt("room_type_id");
                Room_Type roomType = getRoomTypeById(roomTypeId);  // Fetch the Room_Type object

                Room room = new Room(
                        resultSet.getInt("room_id"),
                        resultSet.getInt("hotel_id"),
                        resultSet.getString("room_number"),
                        roomType,  // Pass the Room_Type object
                        resultSet.getString("R_availability_status"),
                        resultSet.getInt("bed_count"),
                        resultSet.getInt("max_occupancy"),
                        resultSet.getInt("room_size"),
                        resultSet.getString("special_amenities")
                );
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public List<Integer> getAvailableRooms(LocalDate startDate, LocalDate endDate) throws SQLException {
        String query = "SELECT room_id FROM Room WHERE room_id NOT IN (" +
                "SELECT room_id FROM Booking WHERE NOT (CheckOutDate <= ? OR CheckInDate >= ?)" +
                ")";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));

            ResultSet rs = stmt.executeQuery();
            List<Integer> availableRoomIds = new ArrayList<>();

            while (rs.next()) {
                availableRoomIds.add(rs.getInt("room_id"));
            }
            return availableRoomIds;
        }
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

            // Use roomType.getRoomTypeId() to get the ID for the Room_Type object
            statement.setInt(8, room.getRoomType().getRoomTypeId());
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

    public boolean existsById(int roomId) throws SQLException {
        // SQL query to check if the room exists
        String query = "SELECT COUNT(*) FROM Room WHERE room_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    public boolean isRoomAvailable(int roomId, LocalDate startDate, LocalDate endDate) throws SQLException {
        // SQL query to check room availability
        String query = "SELECT COUNT(*) FROM Booking WHERE room_id = ? AND NOT (CheckOutDate <= ? OR CheckInDate >= ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            stmt.setDate(2, java.sql.Date.valueOf(startDate));
            stmt.setDate(3, java.sql.Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return false;
    }
    public boolean isRoomAvailableForModification(int roomId, int bookingId, LocalDate startDate, LocalDate endDate) throws SQLException {
        // SQL query to check room availability, excluding the current booking ID
        String query = "SELECT COUNT(*) FROM Booking WHERE room_id = ? AND BookingID != ? AND NOT (CheckOutDate <= ? OR CheckInDate >= ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, roomId);           // Room ID
            stmt.setInt(2, bookingId);        // Exclude the current booking by its ID
            stmt.setDate(3, java.sql.Date.valueOf(startDate));   // Start date of modification
            stmt.setDate(4, java.sql.Date.valueOf(endDate));     // End date of modification
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);  // Room is available if no other bookings overlap the dates
                System.out.println("Number of conflicting bookings: " + count); // Debugging output
                return count == 0;
            }
        }
        return false;  // Room is not available for the new dates
    }
    public Room findById(int roomId) throws SQLException {
        String query = "SELECT * FROM Room WHERE room_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, roomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve the room_type_id and fetch the Room_Type object
                    int roomTypeId = resultSet.getInt("room_type_id");
                    Room_Type roomType = getRoomTypeById(roomTypeId); // Fetch the Room_Type object

                    return new Room(
                            resultSet.getInt("room_id"),
                            resultSet.getInt("hotel_id"),
                            resultSet.getString("room_number"),
                            roomType, // Pass the Room_Type object
                            resultSet.getString("R_availability_status"),
                            resultSet.getInt("bed_count"),
                            resultSet.getInt("max_occupancy"),
                            resultSet.getInt("room_size"),
                            resultSet.getString("special_amenities")
                    );
                } else {
                    return null; // Room not found
                }
            }
        }
    }

    public void updateRoomStatus(Room room) throws SQLException {
        String query = "UPDATE Room SET R_availability_status = ? WHERE room_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, room.getAvailabilityStatus());
            statement.setInt(2, room.getRoomId());
            statement.executeUpdate();
        }
    }

}
