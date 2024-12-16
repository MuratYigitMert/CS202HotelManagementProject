package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Roles;
import com.example.hotelmanagement.Entity.Users;
import com.example.hotelmanagement.repository.EmployeeRepository;
import com.example.hotelmanagement.repository.UsersRepository;

import java.sql.SQLException;
import java.util.Map;

public class AdministratorServiceViewAlltheEmployeesWithTheirRole {

    private final EmployeeRepository employeeRepository;


    // Constructor
    public AdministratorServiceViewAlltheEmployeesWithTheirRole(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Fetches all employees along with their roles.
     *
     * @return A map of Employee objects and their associated Roles.
     * @throws SQLException If there is an error during database interaction.
     */
    public Map<Users, Roles> viewAllEmployeesWithRoles() throws Exception {
        return UsersRepository.getAllEmployeesWithRoles();
    }
}
