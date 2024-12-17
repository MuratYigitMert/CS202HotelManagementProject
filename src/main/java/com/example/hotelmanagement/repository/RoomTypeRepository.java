package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Room_Type;

import java.sql.*;

public class RoomTypeRepository {

    private final Connection connection;

    public RoomTypeRepository(Connection connection) {
        this.connection = connection;
    }

    public Room_Type getRoomTypeById(int roomTypeId) throws SQLException {
        String query = "SELECT * FROM Room_Type WHERE room_type_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, roomTypeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Room_Type(
                        rs.getInt("room_type_id"),
                        rs.getString("type_name"),
                        rs.getDouble("default_price")
                );
            }
        }
        return null;
    }
    public Room_Type getRoomTypeByName(String roomTypeName) throws SQLException {
        Room_Type roomType = null;
        String query = "SELECT * FROM Room_Type WHERE type_name = ?"; // SQL query to fetch Room_Type by typeName

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, roomTypeName); // Set the roomTypeName in the query

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // If a matching room type is found, create a Room_Type object
                    int roomTypeId = resultSet.getInt("room_type_id");
                    String typeName = resultSet.getString("type_name");
                    double defaultPrice = resultSet.getDouble("default_price");

                    roomType = new Room_Type(roomTypeId, typeName, defaultPrice); // Create Room_Type object
                }
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            System.out.println("Error retrieving Room_Type by name: " + e.getMessage());
            throw e;  // Re-throw exception to be handled further up the stack
        }

        return roomType; // Return the Room_Type object or null if not found
    }

}
