package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Administrator;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministratorRepository {
    // INSERT
    final String INSERT_ADMINISTRATOR = "INSERT INTO Administrator (employeeId, accessLevel, department) VALUES(?,?,?)";
    // SELECT
    final String SELECT_ALL_ADMINISTRATORS = "SELECT * FROM Administrator";
    final String SELECT_ADMINISTRATOR_BY_ID = "SELECT * FROM Administrator WHERE employeeId = ?";
    final String AdministratorByDepartment = "SELECT * FROM Administrator WHERE department=?";
    final String getCompanyByAccessLevel = "SELECT * FROM Administrator WHERE accessLevel=?";
    // UPDATE
    final String UPDATE_ADMINISTRATOR = "UPDATE Administrator SET employeeId = ?, accessLevel = ?, department = ? WHERE employeeId = ?";
    //DELETE
    final String deleteAdministrator = "DELETE FROM Administrator WHERE employeeId=?";
    final String deleteAllAdministrator = "DELETE FROM Administrator";

    private final Connection connection;

    public AdministratorRepository(Connection connection) {
        this.connection = connection;
    }

    // INSERT
    public void insertAdministrator(Administrator administrator) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ADMINISTRATOR)) {
            statement.setInt(1, administrator.getEmployeeId());
            statement.setString(2, administrator.getAccessLevel());
            statement.setString(3, administrator.getDepartment());
            statement.executeUpdate();
        }
    }

    // SELECT ALL
    public List<Administrator> getAllAdministrators() throws Exception {
        List<Administrator> administrators = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ADMINISTRATORS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Administrator administrator = new Administrator(
                        resultSet.getInt("employeeId"),
                        resultSet.getString("accessLevel"),
                        resultSet.getString("department")
                );
                administrators.add(administrator);
            }
        }
        return administrators;
    }

    // SELECT BY ID
    public Administrator getAdministratorById(int employeeId) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ADMINISTRATOR_BY_ID)) {
            statement.setInt(1, employeeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Administrator(
                            resultSet.getInt("employeeId"),
                            resultSet.getString("accessLevel"),
                            resultSet.getString("department")
                    );
                }
            }
        }
        return null; // Return null if not found
    }

    // SELECT BY DEPARTMENT
    public List<Administrator> getAdministratorsByDepartment(String department) throws Exception {
        List<Administrator> administrators = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(AdministratorByDepartment)) {
            statement.setString(1, department);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Administrator administrator = new Administrator(
                            resultSet.getInt("employeeId"),
                            resultSet.getString("accessLevel"),
                            resultSet.getString("department")
                    );
                    administrators.add(administrator);
                }
            }
        }
        return administrators;
    }

    // SELECT BY ACCESS LEVEL
    public List<Administrator> getAdministratorsByAccessLevel(String accessLevel) throws Exception {
        List<Administrator> administrators = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(getCompanyByAccessLevel)) {
            statement.setString(1, accessLevel);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Administrator administrator = new Administrator(
                            resultSet.getInt("employeeId"),
                            resultSet.getString("accessLevel"),
                            resultSet.getString("department")
                    );
                    administrators.add(administrator);
                }
            }
        }
        return administrators;
    }

    // UPDATE
    public void updateAdministrator(Administrator administrator, int originalEmployeeId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ADMINISTRATOR)) {
            statement.setInt(1, administrator.getEmployeeId());
            statement.setString(2, administrator.getAccessLevel());
            statement.setString(3, administrator.getDepartment());
            statement.setInt(4, originalEmployeeId);
            statement.executeUpdate();
        }
    }

    // DELETE BY ID
    public void deleteAdministrator(int employeeId) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(deleteAdministrator)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        }
    }

    // DELETE ALL
    public void deleteAllAdministrators() throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(deleteAllAdministrator)) {
            statement.executeUpdate();
        }
    }

}
