package com.example.hotelmanagement.services;
import com.example.hotelmanagement.Entity.Users;
import com.example.hotelmanagement.repository.UsersRepository;

import java.sql.Connection;
import java.util.List;

    public class AdministratorServiceViewUserAccounts {

        private final UsersRepository usersRepository;

        // Constructor
        public AdministratorServiceViewUserAccounts(Connection connection) {
            this.usersRepository = new UsersRepository(connection);
        }

        /**
         * Retrieves and displays all user accounts in the system.
         *
         * @throws Exception If there is an error during the operation.
         */
        public void viewUserAccounts() throws Exception {
            // Retrieve all user accounts
            List<Users> usersList = usersRepository.getAllUsers();

            // Display the user accounts
            if (usersList.isEmpty()) {
                System.out.println("No user accounts found.");
            } else {
                System.out.println("User Accounts:");
                for (Users user : usersList) {
                    System.out.println(user.toString());
                }
            }
        }
    }

