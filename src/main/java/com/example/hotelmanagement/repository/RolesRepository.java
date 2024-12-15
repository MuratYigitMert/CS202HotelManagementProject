package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Roles;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolesRepository {
    // CREATE
    final String INSERT_ROLE = "INSERT INTO Roles (role_id, role_name) VALUES (?, ?)";

    // READ
    final String SELECT_ALL_ROLES = "SELECT * FROM Roles";
    final String SELECT_ROLE_BY_ROLE_ID = "SELECT * FROM Roles WHERE role_id = ?";
    final String SELECT_ROLE_BY_ROLE_NAME = "SELECT * FROM Roles WHERE role_name = ?";

    // UPDATE
    final String UPDATE_ROLE = "UPDATE Roles SET role_name = ? WHERE role_id = ?";

    // DELETE
    final String DELETE_ROLE = "DELETE FROM Roles WHERE role_id = ?";

    // Database connection
    private final Connection connection;

    // Constructor
    public RolesRepository(Connection connection) {
        this.connection = connection;
    }

    // CREATE: Insert a new role
    public void insertRole(Roles role) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ROLE)) {
            statement.setInt(1, role.getRoleId());
            statement.setString(2, role.getRoleName());
            statement.executeUpdate();
        }
    }

    // READ ALL: Get all roles
    public List<Roles> getAllRoles() throws SQLException {
        List<Roles> roles = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ROLES);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Roles role = new Roles(
                        resultSet.getInt("roleId"),
                        resultSet.getString("role_name")
                );
                roles.add(role);
            }
        }
        return roles;
    }

    // READ BY ID: Get a role by its ID
    public Roles getRoleById(int roleId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ROLE_BY_ROLE_ID)) {
            statement.setInt(1, roleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Roles(
                            resultSet.getInt("roleId"),
                            resultSet.getString("role_name")
                    );
                }
            }
        }
        return null;
    }

    // READ BY NAME: Get a role by its name
    public List<Roles> getRolesByName(String roleName) throws SQLException {
        List<Roles> roles = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ROLE_BY_ROLE_NAME)) {
            statement.setString(1, roleName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Roles role = new Roles(
                            resultSet.getInt("roleId"),
                            resultSet.getString("role_name")
                    );
                    roles.add(role);
                }
            }
        }
        return roles;
    }

    // UPDATE: Update an existing role
    public void updateRole(Roles role) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE)) {
            statement.setString(1, role.getRoleName());
            statement.setInt(2, role.getRoleId());
            statement.executeUpdate();
        }
    }

    // DELETE: Delete a role by its ID
    public void deleteRole(int roleId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ROLE)) {
            statement.setInt(1, roleId);
            statement.executeUpdate();
        }
    }
}

