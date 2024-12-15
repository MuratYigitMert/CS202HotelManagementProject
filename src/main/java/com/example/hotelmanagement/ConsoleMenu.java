package com.example.hotelmanagement;

import com.example.hotelmanagement.Entity.Booking;
import com.example.hotelmanagement.Entity.Room;
import com.example.hotelmanagement.services.AddBooking;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.GuestRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import com.example.hotelmanagement.DatabaseConnection;
import com.example.hotelmanagement.services.DeleteBooking;
import com.example.hotelmanagement.services.ModifyBooking;
import com.example.hotelmanagement.services.ViewRoomAvailability;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleMenu {
    Connection connection = DatabaseConnection.getConnection();
    private final AddBooking addBookingService;
    private final GuestRepository guestRepository  = new GuestRepository(connection);
    private final RoomRepository roomRepository = new RoomRepository(connection);
    private final BookingRepository bookingRepository = new BookingRepository(connection);
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        // Create repositories (this should be done via Dependency Injection ideally)
        BookingRepository bookingRepository = new BookingRepository(connection);
        GuestRepository guestRepository = new GuestRepository(connection);
        RoomRepository roomRepository = new RoomRepository(connection);

        // Create AddBooking service
        AddBooking addBookingService = new AddBooking(bookingRepository, guestRepository, roomRepository);

        // Initialize and run the menu
        ConsoleMenu menu = new ConsoleMenu(addBookingService);
        menu.run();
    }

    // Constructor with dependency injection for AddBooking service
    public ConsoleMenu(AddBooking addBookingService) {
        this.addBookingService = addBookingService;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 7) {
            printMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                handleChoice(choice, scanner);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
            }
        }

        scanner.close();
        System.out.println("Exiting... Thank you for using the Hotel Management System!");
    }

    private void printMenu() {
        System.out.println("\nWelcome to the Hotel Management System");
        System.out.println("Please select an option:");
        System.out.println("1. Add a new booking");
        System.out.println("2. Modify a booking");
        System.out.println("3. Delete a booking");
        System.out.println("4. View room availability");
        System.out.println("5. Check-in");
        System.out.println("6. Check-out");
        System.out.println("7. Exit");
    }

    private void handleChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                addBooking(scanner);
                break;
            case 2:
                modifyBooking(scanner);
                break;
            case 3:
                deleteBooking(scanner);
                break;
            case 4:
                viewRoomAvailability(scanner);
                break;
            case 5:
                checkIn(scanner);
                break;
            case 6:
                checkOut(scanner);
                break;
            case 7:
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void addBooking(Scanner scanner) {
        // Collect booking details from the user
        System.out.print("Enter guest ID: ");
        int guestId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter room ID: ");
        int roomId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter check-in date (yyyy-MM-dd): ");
        String checkInDateStr = scanner.nextLine();
        System.out.print("Enter check-out date (yyyy-MM-dd): ");
        String checkOutDateStr = scanner.nextLine();
        java.sql.Date checkInDate = java.sql.Date.valueOf(checkInDateStr);
        java.sql.Date checkOutDate = java.sql.Date.valueOf(checkOutDateStr);
        int canceledBy = 0;

        // Create Booking object
        Booking booking = new Booking("Pending", checkInDate, checkOutDate, guestId, roomId, canceledBy);


        // Call the service to add the booking
        String result = addBookingService.addBooking(booking);
        System.out.println(result);
        System.out.println("1. Continue with other operations");
        System.out.println("2. Exit");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 2) {
            System.out.println("Goodbye!");
            System.exit(0); // Exit the program
        } else if (choice == 1) {
            // The loop will continue, so the user can proceed with other operations
        } else {
            System.out.println("Invalid choice. Please select 1 or 2.");
        }
    }

    private void modifyBooking(Scanner scanner) {
        System.out.print("Enter the Booking ID to modify: ");
        int bookingId = Integer.parseInt(scanner.nextLine());

        // Retrieve the current booking details to display to the user
        try {
            Booking existingBooking = bookingRepository.findById(bookingId);
            if (existingBooking == null) {
                System.out.println("Error: No booking found with ID " + bookingId);
                return;
            }

            // Display existing details
            System.out.println("Current Booking Details:");
            System.out.println("Booking ID: " + existingBooking.getBookingId());
            System.out.println("Booking Status: " + existingBooking.getBookingStatus());
            System.out.println("Check-In Date: " + existingBooking.getCheckInDate());
            System.out.println("Check-Out Date: " + existingBooking.getCheckOutDate());
            System.out.println("Guest ID: " + existingBooking.getGuestId());
            System.out.println("Room ID: " + existingBooking.getRoomId());
            System.out.println("Canceled By: " + existingBooking.getCanceledBy());

            // Ask for new values
            System.out.println("\nEnter new values or press Enter to keep the current value:");

            // New Room ID
            System.out.print("New Room ID: ");
            String roomIdStr = scanner.nextLine();
            int newRoomId = roomIdStr.isEmpty() ? existingBooking.getRoomId() : Integer.parseInt(roomIdStr);

            // New Check-In Date
            System.out.print("New Check-In Date (yyyy-MM-dd): ");
            String newCheckInDateStr = scanner.nextLine();
            newCheckInDateStr = newCheckInDateStr.isEmpty() ? existingBooking.getCheckInDate().toString() : newCheckInDateStr;

            // New Check-Out Date
            System.out.print("New Check-Out Date (yyyy-MM-dd): ");
            String newCheckOutDateStr = scanner.nextLine();
            newCheckOutDateStr = newCheckOutDateStr.isEmpty() ? existingBooking.getCheckOutDate().toString() : newCheckOutDateStr;

            // Create ModifyBooking instance and call the modifyBooking method
            ModifyBooking modifyBookingService = new ModifyBooking(bookingRepository, guestRepository, roomRepository);

            // Now call modifyBooking with the required parameters
            String result = modifyBookingService.modifyBooking(
                    bookingId,
                    newRoomId,
                    newCheckInDateStr,
                    newCheckOutDateStr
            );

            System.out.println(result);

        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve or modify booking. Details: " + e.getMessage());
        }
    }



    private void deleteBooking(Scanner scanner) {
        System.out.print("Enter the Booking ID to delete: ");
        int bookingId = Integer.parseInt(scanner.nextLine());

        // Create DeleteBooking instance and call the deleteBooking method
        DeleteBooking deleteBookingService = new DeleteBooking(bookingRepository);

        // Now call deleteBooking with the required parameter
        String result = deleteBookingService.deleteBooking(bookingId);
        System.out.println(result);
    }


    public void viewRoomAvailability(Scanner scanner) {
        System.out.print("Enter the start date (yyyy-MM-dd): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter the end date (yyyy-MM-dd): ");
        String endDate = scanner.nextLine();

        // Create ViewRoomAvailability instance
        ViewRoomAvailability viewRoomAvailabilityService = new ViewRoomAvailability(roomRepository);

        // Get available rooms
        String result = viewRoomAvailabilityService.viewAvailableRooms(startDate, endDate);
        System.out.println(result);
    }


    private void checkIn(Scanner scanner) {
        System.out.print("Enter the Booking ID to check in: ");
        int bookingId = Integer.parseInt(scanner.nextLine());

        // Retrieve the current booking details
        try {
            Booking booking = bookingRepository.findById(bookingId);
            if (booking == null) {
                System.out.println("Error: No booking found with ID " + bookingId);
                return;
            }
            //System.out.println("Booking Status: [" + booking.getBookingStatus() + "]");
            // Check if the booking status is "Pending"
            if ("Pending".equals(booking.getBookingStatus().trim())) {
                // Update booking status to "Checked-In"
                booking.setBookingStatus("Checked-In");
                bookingRepository.updateBookingStatus(booking);

                // Update room status to "Occupied"
                Room room = roomRepository.findById(booking.getRoomId());
                if (room != null) {
                    room.setAvailabilityStatus("Occupied");
                    roomRepository.updateRoomStatus(room);
                }

                System.out.println("Booking successfully checked in.");
            } else {
                System.out.println("Booking cannot be checked in because its status is not 'Pending'.");
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to check in. Details: " + e.getMessage());
        }
    }


    private void checkOut(Scanner scanner) {
        // TODO: Connect to bookingService for check-out
        System.out.println("Check-out functionality here.");
    }
}
