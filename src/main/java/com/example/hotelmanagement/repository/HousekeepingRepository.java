package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Housekeeping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HousekeepingRepository {

    // INSERT
    final String INSERT_HOUSEKEEPER = "INSERT INTO Housekeeping (employee_id, cleaning_area, shift) VALUES (?, ?, ?)";

    // SELECT
    final String SELECT_ALL_HOUSEKEEPERS = "SELECT * FROM Housekeeping";
    final String SELECT_HOUSEKEEPER_BY_ID = "SELECT * FROM Housekeeping WHERE employee_id = ?";

    // UPDATE
    final String UPDATE_HOUSEKEEPER = "UPDATE Housekeeping SET cleaning_area = ?, shift = ? WHERE employee_id = ?";

    // DELETE
    final String DELETE_HOUSEKEEPER = "DELETE FROM Housekeeping WHERE employee_id = ?";

    // Connection to the database
    private final Connection connection;

    // Constructor to initialize the connection
    public HousekeepingRepository(Connection connection) {
        this.connection = connection;
    }

    // CREATE: Insert a housekeeper into the database
    public void insertHousekeeper(Housekeeping housekeeping) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_HOUSEKEEPER)) {
            statement.setInt(1, housekeeping.getEmployeeId());
            statement.setString(2, housekeeping.getCleaningArea());
            statement.setString(3, housekeeping.getShift());
            statement.executeUpdate();
        }
    }

    // READ ALL: Get all housekeepers from the database
    public List<Housekeeping> getAllHousekeepers() throws SQLException {
        List<Housekeeping> housekeepers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_HOUSEKEEPERS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Housekeeping housekeeping = new Housekeeping(
                        resultSet.getInt("employeeId"),
                        resultSet.getString("cleaningArea"),
                        resultSet.getString("shift")
                );
                housekeepers.add(housekeeping);
            }
        }
        return housekeepers;
    }

    // READ BY ID: Get a housekeeper by employeeId
    public Housekeeping getHousekeeperById(int employeeId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_HOUSEKEEPER_BY_ID)) {
            statement.setInt(1, employeeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Housekeeping(
                            resultSet.getInt("employeeId"),
                            resultSet.getString("cleaningArea"),
                            resultSet.getString("shift")
                    );
                }
            }
        }
        return null;
    }

    // UPDATE: Update a housekeeper's information in the database
    public void updateHousekeeper(Housekeeping housekeeping) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_HOUSEKEEPER)) {
            statement.setString(1, housekeeping.getCleaningArea());
            statement.setString(2, housekeeping.getShift());
            statement.setInt(3, housekeeping.getEmployeeId());
            statement.executeUpdate();
        }
    }

    // DELETE: Delete a housekeeper from the database by employeeId
    public void deleteHousekeeper(int employeeId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_HOUSEKEEPER)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        }
    }
    public List<Housekeeping> getAllHousekeepersWithAvailability() throws SQLException {
        String query = """
                SELECT h.employee_id, h.cleaning_area, h.shift, hs.status
                FROM Housekeeping h
                LEFT JOIN Housekeeping_Schedule hs ON h.employee_id = hs.housekeeping_id;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Housekeeping> housekeepers = new ArrayList<>();
                while (resultSet.next()) {
                    int employeeId = resultSet.getInt("employee_id");
                    String cleaningArea = resultSet.getString("cleaning_area");
                    String shift = resultSet.getString("shift");
                    String status = resultSet.getString("status");

                    // Construct a Housekeeping object for each record
                    Housekeeping housekeeping = new Housekeeping(employeeId, cleaningArea, shift);
                    housekeeping.setShift(status != null ? status : "Available");
                    housekeepers.add(housekeeping);
                }
                return housekeepers;
            }
        }
    }
}
