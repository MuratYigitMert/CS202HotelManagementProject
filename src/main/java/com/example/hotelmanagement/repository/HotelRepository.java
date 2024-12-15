package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelRepository {
    // CREATE
    final String INSERT_HOTEL = "INSERT INTO Hotel (H_Name, H_Address, H_City, H_phone_number, H_Email) VALUES (?, ?, ?, ?, ?)";

    // READ
    final String SELECT_ALL_HOTELS = "SELECT * FROM Hotel";
    final String SELECT_HOTEL_BY_ID = "SELECT * FROM Hotel WHERE Hotel_ID = ?";

    // UPDATE
    final String UPDATE_HOTEL = "UPDATE Hotel SET H_Name = ?, H_Address = ?, H_City = ?, H_phone_number = ?, H_Email = ? WHERE Hotel_ID = ?";

    // DELETE
    final String DELETE_HOTEL = "DELETE FROM Hotel WHERE Hotel_ID = ?";
    final String DELETE_ALL = "DELETE FROM Hotel";

    // Connection field
    private final Connection connection;

    // Constructor to initialize connection
    public HotelRepository(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void insertHotel(Hotel hotel) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_HOTEL)) {
            statement.setString(1, hotel.getHotelName());
            statement.setString(2, hotel.getHotelAddress());
            statement.setString(3, hotel.getHotelCity());
            statement.setString(4, hotel.getPhoneNumber());
            statement.setString(5, hotel.getEmail());
            statement.executeUpdate();
        }
    }

    // READ ALL
    public List<Hotel> getAllHotels() throws Exception {
        List<Hotel> hotels = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_HOTELS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Hotel hotel = new Hotel(
                        resultSet.getInt("Hotel_ID"),
                        resultSet.getString("H_Name"),
                        resultSet.getString("H_Address"),
                        resultSet.getString("H_City"),
                        resultSet.getString("H_phone_number"),
                        resultSet.getString("H_Email")
                );
                hotels.add(hotel);
            }
        }
        return hotels;
    }

    // READ BY HOTEL ID
    public Hotel getHotelById(int hotelId) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_HOTEL_BY_ID)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Hotel(
                            resultSet.getInt("Hotel_ID"),
                            resultSet.getString("H_Name"),
                            resultSet.getString("H_Address"),
                            resultSet.getString("H_City"),
                            resultSet.getString("H_phone_number"),
                            resultSet.getString("H_Email")
                    );
                }
            }
        }
        return null;
    }

    // UPDATE
    public void updateHotel(Hotel hotel) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_HOTEL)) {
            statement.setString(1, hotel.getHotelName());
            statement.setString(2, hotel.getHotelAddress());
            statement.setString(3, hotel.getHotelCity());
            statement.setString(4, hotel.getPhoneNumber());
            statement.setString(5, hotel.getEmail());
            statement.setInt(6, hotel.getHotelId());
            statement.executeUpdate();
        }
    }

    // DELETE BY ID
    public void deleteHotel(int hotelId) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_HOTEL)) {
            statement.setInt(1, hotelId);
            statement.executeUpdate();
        }
    }

    // DELETE ALL
    public void deleteAllHotels() throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL)) {
            statement.executeUpdate();
        }
    }
}
