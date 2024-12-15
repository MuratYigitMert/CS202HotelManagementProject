package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Guest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestRepository {
    // CREATE
    final String INSERT_GUEST = "INSERT INTO Guest (guest_Id, user_Id, identification_number) VALUES (?, ?, ?)";

    // READ
    final String SELECT_ALL_GUESTS = "SELECT * FROM Guest";
    final String SELECT_GUEST_BY_guest_id= "SELECT * FROM Guest WHERE guest_id = ?";
    final String SELECT_GUEST_BY_user_id = "SELECT * FROM Guest WHERE user_id = ?";

    // UPDATE
    final String UPDATE_GUEST = "UPDATE Guest SET user_id = ?, identification_number = ? WHERE guest_id = ?";

    // DELETE
    final String DELETE_GUEST = "DELETE FROM Guest WHERE guest_id = ?";
    final String DELETE_ALL = "DELETE FROM Guest";

    private final Connection connection;

    public GuestRepository(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void insertGuest(Guest guest) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GUEST)) {
            statement.setInt(1, guest.getGuestId());
            statement.setInt(2, guest.getUserId());
            statement.setString(3, guest.getIdentificationNumber());
            statement.executeUpdate();
        }
    }

    // READ ALL
    public List<Guest> getAllGuests() throws Exception {
        List<Guest> guests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GUESTS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Guest guest = new Guest(
                        resultSet.getInt("guestId"),
                        resultSet.getInt("userId"),
                        resultSet.getString("identificationNumber")
                );
                guests.add(guest);
            }
        }
        return guests;
    }

    // READ BY GUEST ID
    public Guest getGuestById(int guestId) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_GUEST_BY_guest_id)) {
            statement.setInt(1, guestId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Guest(
                            resultSet.getInt("guestId"),
                            resultSet.getInt("userId"),
                            resultSet.getString("identificationNumber")
                    );
                }
            }
        }
        return null;
    }

    // READ BY USER ID
    public List<Guest> getGuestsByUserId(int userId) throws Exception {
        List<Guest> guests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_GUEST_BY_user_id)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Guest guest = new Guest(
                            resultSet.getInt("guestId"),
                            resultSet.getInt("userId"),
                            resultSet.getString("identificationNumber")
                    );
                    guests.add(guest);
                }
            }
        }
        return guests;
    }

    // UPDATE
    public void updateGuest(Guest guest) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GUEST)) {
            statement.setInt(1, guest.getUserId());
            statement.setString(2, guest.getIdentificationNumber());
            statement.setInt(3, guest.getGuestId());
            statement.executeUpdate();
        }
    }

    // DELETE BY ID
    public void deleteGuest(int guestId) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_GUEST)) {
            statement.setInt(1, guestId);
            statement.executeUpdate();
        }
    }

    // DELETE ALL
    public void deleteAllGuests() throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL)) {
            statement.executeUpdate();
        }
    }
}

