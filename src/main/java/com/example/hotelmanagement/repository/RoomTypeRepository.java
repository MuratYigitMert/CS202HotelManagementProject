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
}
