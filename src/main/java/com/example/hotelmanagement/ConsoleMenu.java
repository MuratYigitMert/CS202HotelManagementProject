package com.example.hotelmanagement;

import com.example.hotelmanagement.Entity.Booking;
import com.example.hotelmanagement.Entity.Room;
import com.example.hotelmanagement.repository.PaymentsRepository;
import com.example.hotelmanagement.services.*;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.GuestRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import com.example.hotelmanagement.services.ProcessPayment;
import com.example.hotelmanagement.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleMenu {
    private final Connection connection = DatabaseConnection.getConnection();
    private final AddBooking addBookingService;
    private final GuestRepository guestRepository = new GuestRepository(connection);
    private final RoomRepository roomRepository = new RoomRepository(connection);
    private final BookingRepository bookingRepository = new BookingRepository(connection);
    private final ViewMyBookings viewMyBookingsService = new ViewMyBookings(bookingRepository);
    private final CancelBooking cancelBookingService = new CancelBooking(bookingRepository);
    private final PaymentsRepository paymentsRepository = new PaymentsRepository(connection);
    private final  ProcessPayment processPaymentService = new ProcessPayment(paymentsRepository);
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();

        // Initialize services
        BookingRepository bookingRepository = new BookingRepository(connection);
        GuestRepository guestRepository = new GuestRepository(connection);
        RoomRepository roomRepository = new RoomRepository(connection);
        AddBooking addBookingService = new AddBooking(bookingRepository, guestRepository, roomRepository);

        // Run the menu
        ConsoleMenu menu = new ConsoleMenu(addBookingService);
        menu.mainMenu();
    }

    public ConsoleMenu(AddBooking addBookingService) {
        this.addBookingService = addBookingService;
    }

    // Main menu for user role selection
    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 5) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Guest Menu");
            System.out.println("2. Administrator Menu");
            System.out.println("3. Receptionist Menu");
            System.out.println("4. Housekeeping Menu");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> guestMenu(scanner);
                    case 2 -> adminMenu(scanner);
                    case 3 -> receptionistMenu(scanner);
                    case 4 -> housekeepingMenu(scanner);
                    case 5 -> System.out.println("Exiting the system. Goodbye!");
                    default -> System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // Guest menu with consolidated functionality
    private void guestMenu(Scanner scanner) {
        int choice = -1;

        while (choice != 5) {
            System.out.println("\n--- Guest Menu ---");
            System.out.println("1. Add New Booking");
            System.out.println("2. View Available Rooms");
            System.out.println("3. View My Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Exit to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addBooking(scanner);
                    case 2 -> viewRoomAvailability(scanner);
                    case 3 -> viewMyBookings(scanner); // Placeholder for View My Bookings
                    case 4 -> cancelBooking(scanner); // Placeholder for Cancel Booking
                    case 5 -> System.out.println("Returning to Main Menu...");
                    default -> System.out.println("Invalid choice. Please select a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // Add new booking
    private void addBooking(Scanner scanner) {
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

        Booking booking = new Booking("Pending", checkInDate, checkOutDate, guestId, roomId, 0);
        String result = addBookingService.addBooking(booking);
        System.out.println(result);
    }

    // View available rooms
    private void viewRoomAvailability(Scanner scanner) {
        System.out.print("Enter the start date (yyyy-MM-dd): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter the end date (yyyy-MM-dd): ");
        String endDate = scanner.nextLine();

        ViewRoomAvailability viewRoomAvailabilityService = new ViewRoomAvailability(roomRepository);
        String result = viewRoomAvailabilityService.viewAvailableRooms(startDate, endDate);
        System.out.println(result);
    }

    private void viewMyBookings(Scanner scanner) {
        System.out.print("Enter your Guest ID: ");
        int guestId = Integer.parseInt(scanner.nextLine());

        String result = viewMyBookingsService.getBookingsByGuestId(guestId);
        System.out.println(result);
    }


    private void cancelBooking(Scanner scanner) {
        System.out.print("Enter your Guest ID: ");
        int guestId = Integer.parseInt(scanner.nextLine());

        String result = cancelBookingService.cancelBookingByGuestId(guestId, scanner);
        System.out.println(result);
    }


    private void adminMenu(Scanner scanner) {
        System.out.println("\n--- Administrator Menu ---");
        // TODO: Implement the Administrator Menu
    }

    private void receptionistMenu(Scanner scanner) {
        int choice = -1;


        while (choice != 6) {
            System.out.println("\n--- Receptionist Menu ---");
            System.out.println("1. Add New Booking");
            System.out.println("2. Modify Booking");
            System.out.println("3. Delete Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Process Payment");
            System.out.println("6. Exit to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addBooking(scanner);
                    case 2 -> modifyBooking(scanner);
                    case 3 -> cancelBooking(scanner);
                    case 4 -> viewBookings(scanner);
                    case 5 -> processPayment(scanner);
                    case 6 -> System.out.println("Returning to Main Menu...");
                    default -> System.out.println("Invalid choice. Please select a number between 1 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void viewBookings(Scanner scanner) {
        // Create the ViewBookings service
        ViewBookings viewBookingsService = new ViewBookings(bookingRepository);

        // View all bookings
        viewBookingsService.viewAllBookings();

        // Allow receptionist to return to the menu
        System.out.println("1. Return to Receptionist Menu");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1) {
            // Recurse to return to the receptionist menu
        } else {
            System.out.println("Invalid choice. Returning to Receptionist Menu...");
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

            // Check if the booking status is "Pending"
            if ("Pending".equals(booking.getBookingStatus().trim())) {
                // Update booking status to "Checked-In"
                booking.setBookingStatus("Checked-In");
                bookingRepository.updateBookingStatus(booking);

                // Update room status to "Occupied"
                Room room = roomRepository.getRoomById(booking.getRoomId());
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
    private void processPayment(Scanner scanner) {
        try {
            System.out.print("Enter Guest ID to process payment: ");
            int guestId = Integer.parseInt(scanner.nextLine());
            String result = processPaymentService.processPayment(guestId, scanner);
            System.out.println(result);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid Guest ID.");
        } catch (Exception e) {
            System.out.println("An error occurred while processing the payment: " + e.getMessage());
        }
    }



    private void housekeepingMenu(Scanner scanner) {
        System.out.println("\n--- Housekeeping Menu ---");
        // TODO: Implement the Housekeeping Menu
    }
}
