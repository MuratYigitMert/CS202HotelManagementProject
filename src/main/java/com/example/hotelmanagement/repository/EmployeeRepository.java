package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EmployeeRepository  {
        // CREATE
        final String INSERT_EMPLOYEE = "INSERT INTO Employee (employee_id, user_id, hotel_id, role_id, salary) VALUES (?,?, ?, ?, ?)";

        // READ
        final String SELECT_ALL_EMPLOYEES = "SELECT * FROM Employee";
        final String SELECT_EMPLOYEE_BY_employee_id = "SELECT * FROM Employee WHERE employee_id = ?";
        final String SELECT_EMPLOYEE_BY_hotel_id = "SELECT * FROM Employee WHERE hotel_id = ?";
        final String SELECT_EMPLOYEE_BY_role_id = "SELECT * FROM Employee WHERE role_id = ?";

        // UPDATE
       final  String UPDATE_EMPLOYEE = "UPDATE Employee SET user_id = ?, hotel_id = ?, role_id = ?, salary = ? WHERE employee_id = ?";

        // DELETE
        final String DELETE_EMPLOYEE = "DELETE FROM Employee WHERE employee_id = ?";
        final String DELETE_All= "DELETE FROM Employee";
    private final Connection connection;
    public EmployeeRepository(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void insertEmployee(Employee employee) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_EMPLOYEE)) {
            statement.setInt(1, employee.getUserId());
            statement.setInt(2, employee.getHotelId());
            statement.setInt(3, employee.getRoleId());
            statement.setDouble(4, employee.getSalary());
            statement.executeUpdate();
        }
    }
    public void ben_handsome(Employee employee){

    }

    // READ ALL
    public List<Employee> getAllEmployees() throws Exception {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getInt("employeeId"),
                        resultSet.getInt("userId"),
                        resultSet.getInt("hotelId"),
                        resultSet.getInt("roleId"),
                        resultSet.getDouble("salary")
                );
                employees.add(employee);
            }
        }
        return employees;
    }

    // READ BY ID
    public Employee getEmployeeById(int employeeId) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_EMPLOYEE_BY_employee_id)) {
            statement.setInt(1, employeeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Employee(
                            resultSet.getInt("employeeId"),
                            resultSet.getInt("userId"),
                            resultSet.getInt("hotelId"),
                            resultSet.getInt("roleId"),
                            resultSet.getDouble("salary")
                    );
                }
            }
        }
        return null;
    }

    // READ BY HOTEL ID
    public List<Employee> getEmployeesByHotelId(int hotelId) throws Exception {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_EMPLOYEE_BY_hotel_id)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Employee employee = new Employee(
                            resultSet.getInt("employeeId"),
                            resultSet.getInt("userId"),
                            resultSet.getInt("hotelId"),
                            resultSet.getInt("roleId"),
                            resultSet.getDouble("salary")
                    );
                    employees.add(employee);
                }
            }
        }
        return employees;
    }

    // READ BY ROLE ID
    public List<Employee> getEmployeesByRoleId(int roleId) throws Exception {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_EMPLOYEE_BY_role_id)) {
            statement.setInt(1, roleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Employee employee = new Employee(
                            resultSet.getInt("employeeId"),
                            resultSet.getInt("userId"),
                            resultSet.getInt("hotelId"),
                            resultSet.getInt("roleId"),
                            resultSet.getDouble("salary")
                    );
                    employees.add(employee);
                }
            }
        }
        return employees;
    }

    // UPDATE
    public void updateEmployee(Employee employee) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE)) {
            statement.setInt(1, employee.getUserId());
            statement.setInt(2, employee.getHotelId());
            statement.setInt(3, employee.getRoleId());
            statement.setDouble(4, employee.getSalary());
            statement.setInt(5, employee.getEmployeeId());
            statement.executeUpdate();
        }
    }

    // DELETE BY ID
    public void deleteEmployee(int employeeId) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        }
    }

    // DELETE ALL
    public void deleteAllEmployees() throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_All)) {
            statement.executeUpdate();
        }
    }

    }



