package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Receptionist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceptionistRepository {
    // INSERT
    final String INSERT_RECEPTIONIST = "INSERT INTO Receptionist (employee_id, Languages_spoken, Work_shift) VALUES (?, ?, ?)";

    // SELECT
    final String SELECT_ALL_RECEPTIONISTS = "SELECT * FROM Receptionist";
    final String SELECT_RECEPTIONIST_BY_ID = "SELECT * FROM Receptionist WHERE employee_id = ?";

    // UPDATE
    final String UPDATE_RECEPTIONIST = "UPDATE Receptionist SET Languages_spoken = ?, Work_shift = ? WHERE employee_id = ?";

    // DELETE
    final String DELETE_RECEPTIONIST = "DELETE FROM Receptionist WHERE employee_id = ?";

    // Database connection
    private final Connection connection;

    // Constructor
    public ReceptionistRepository(Connection connection) {
        this.connection = connection;
    }

    // CREATE: Insert a new receptionist
    public void insertReceptionist(Receptionist receptionist) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_RECEPTIONIST)) {
            statement.setInt(1, receptionist.getEmployeeId());
            statement.setString(2, receptionist.getLanguagesSpoken());
            statement.setString(3, receptionist.getWorkShift());
            statement.executeUpdate();
        }
    }

    // READ ALL: Get all receptionists
    public List<Receptionist> getAllReceptionists() throws SQLException {
        List<Receptionist> receptionists = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_RECEPTIONISTS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Receptionist receptionist = new Receptionist(
                        resultSet.getInt("employeeId"),
                        resultSet.getString("languagesSpoken"),
                        resultSet.getString("workShift")
                );
                receptionists.add(receptionist);
            }
        }
        return receptionists;
    }

    // READ BY ID: Get a specific receptionist by employee ID
    public Receptionist getReceptionistById(int employeeId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_RECEPTIONIST_BY_ID)) {
            statement.setInt(1, employeeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Receptionist(
                            resultSet.getInt("employeeId"),
                            resultSet.getString("languagesSpoken"),
                            resultSet.getString("workShift")
                    );
                }
            }
        }
        return null;
    }

    // UPDATE: Update an existing receptionist's information
    public void updateReceptionist(Receptionist receptionist) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_RECEPTIONIST)) {
            statement.setString(1, receptionist.getLanguagesSpoken());
            statement.setString(2, receptionist.getWorkShift());
            statement.setInt(3, receptionist.getEmployeeId());
            statement.executeUpdate();
        }
    }

    // DELETE: Delete a receptionist by employee ID
    public void deleteReceptionist(int employeeId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_RECEPTIONIST)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        }
    }
}
