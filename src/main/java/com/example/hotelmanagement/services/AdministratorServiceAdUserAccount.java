package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Users;
import com.example.hotelmanagement.repository.UsersRepository;

import java.sql.Connection;
import java.sql.Date;

public class AdministratorServiceAdUserAccount {

    private final UsersRepository usersRepository;

    // Constructor
    public AdministratorServiceAdUserAccount(Connection connection) {
        this.usersRepository = new UsersRepository(connection);
    }

    /**
     * Adds a new user account to the system.
     *
     * @param firstName   The user's first name.
     * @param lastName    The user's last name.
     * @param phoneNumber The user's phone number.
     * @param email       The user's email address.
     * @param dateOfBirth The user's date of birth.
     * @throws Exception If there is an error during the operation.
     */
    public void addUserAccount(String firstName, String lastName, String phoneNumber, String email, Date dateOfBirth) throws Exception {
        // Validate input
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty.");
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }
        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        if (dateOfBirth == null || dateOfBirth.after(new java.util.Date())) {
            throw new IllegalArgumentException("Invalid date of birth.");
        }

        // Create a new Users entity
        Users newUser = new Users(0, firstName, lastName, phoneNumber, email, dateOfBirth);

        // Add the user account using the repository
        usersRepository.addUser(newUser);
        System.out.println("User account successfully added.");
    }
}
