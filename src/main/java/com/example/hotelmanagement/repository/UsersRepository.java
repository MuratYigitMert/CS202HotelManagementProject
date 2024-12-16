package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Roles;
import com.example.hotelmanagement.Entity.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersRepository {

    // SQL Queries
    private static final String INSERT_USER =
            "INSERT INTO Users (first_name, last_name, phone_number, email, date_of_birth) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID =
            "SELECT user_id, first_name, last_name, phone_number, email, date_of_birth FROM Users WHERE user_id = ?";
    private static final String SELECT_ALL_USERS =
            "SELECT user_id, first_name, last_name, phone_number, email, date_of_birth FROM Users";
    private static final String UPDATE_USER =
            "UPDATE Users SET first_name = ?, last_name = ?, phone_number = ?, email = ?, date_of_birth = ? WHERE user_id = ?";
    private static final String DELETE_USER =
            "DELETE FROM Users WHERE user_id = ?";
    private static final String DELETE_ALL_USERS =
            "DELETE FROM Users";

    private final Connection connection;

    // Constructor
    public UsersRepository(Connection connection) {
        this.connection = connection;
    }

    // CREATE: Add a new user
    public void addUser(Users user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getEmail());
            statement.setDate(5, user.getDateOfBirth());
            statement.executeUpdate();
        }
    }

    // READ: Get a user by ID
    public Users getUserById(int userId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Users(
                            resultSet.getInt("user_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("email"),
                            resultSet.getDate("date_of_birth")
                    );
                }
            }
        }
        return null; // User not found
    }

    // READ: Get all users
    public List<Users> getAllUsers() throws SQLException {
        List<Users> usersList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Users user = new Users(
                        resultSet.getInt("user_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getDate("date_of_birth")
                );
                usersList.add(user);
            }
        }
        return usersList;
    }

    // UPDATE: Update user details
    public void updateUser(Users user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getEmail());
            statement.setDate(5, user.getDateOfBirth());
            statement.setInt(6, user.getUserId());
            statement.executeUpdate();
        }
    }

    // DELETE: Delete a user by ID
    public void deleteUser(int userId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }

    // DELETE ALL: Delete all users
    public void deleteAllUsers() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL_USERS)) {
            statement.executeUpdate();
        }
    }

    public static Map<Users, Roles> getAllEmployeesWithRoles() throws Exception {
        String query = """
        SELECT u.user_id, u.first_name, u.last_name, u.phone_number, u.email, u.date_of_birth,
               r.role_id, r.role_name
        FROM Users u
        JOIN Employee e ON u.user_id = e.user_id
        JOIN Roles r ON e.role_id = r.role_id
        """;

        Map<Users, Roles> userRoles = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Users nesnesi oluştur
                Users user = new Users(
                        resultSet.getInt("user_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getDate("date_of_birth")
                );

                // Roles nesnesi oluştur
                Roles role = new Roles(
                        resultSet.getInt("role_id"),
                        resultSet.getString("role_name")
                );

                // Map'e ekle
                userRoles.put(user, role);
            }
        }
        return userRoles;
    }

}
