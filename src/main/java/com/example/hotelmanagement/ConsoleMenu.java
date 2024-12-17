package com.example.hotelmanagement;

import com.example.hotelmanagement.Entity.*;
import com.example.hotelmanagement.repository.*;
import com.example.hotelmanagement.services.*;
import com.example.hotelmanagement.services.ProcessPayment;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleMenu {
    private final Connection connection = DatabaseConnection.getConnection();
    private final AddBooking addBookingService;
    private final EmployeeRepository employeeRepository=new EmployeeRepository(connection);
    private final HousekeepingRepository housekeepingRepository=new HousekeepingRepository(connection);
    private final GuestRepository guestRepository = new GuestRepository(connection);
    private final RoomRepository roomRepository = new RoomRepository(connection);
    private final BookingRepository bookingRepository = new BookingRepository(connection);
    private final ViewMyBookings viewMyBookingsService = new ViewMyBookings(bookingRepository);
    private final CancelBooking cancelBookingService = new CancelBooking(bookingRepository);
    private final PaymentsRepository paymentsRepository = new PaymentsRepository(connection);
    private final ProcessPayment processPaymentService = new ProcessPayment(paymentsRepository);
    private final HousekeepingScheduleRepository housekeepingScheduleRepository= new HousekeepingScheduleRepository(connection);
    private final HousekeepingScheduleService housekeepingScheduleService= new HousekeepingScheduleService(housekeepingScheduleRepository);
    private final HousekeepingRepository hkeeperRecAvaService = new HousekeepingRepository(connection);
    private final RoomTypeRepository roomTypeRepository= new RoomTypeRepository(connection);
    private final AdministratorServiceDeleteRoom administratorServiceDeleteRoom=new AdministratorServiceDeleteRoom(roomRepository, bookingRepository);
    private final AdministratorServiceManageRoomStatus administratorServiceManageRoomStatus=  new AdministratorServiceManageRoomStatus(roomRepository);
    private final AdministratorServiceAdUserAccount administratorServiceAdUserAccount= new AdministratorServiceAdUserAccount(connection);
    private final AdministratorServiceViewUserAccounts administratorServiceViewUserAccounts= new AdministratorServiceViewUserAccounts(connection);
    private final AdministratorServiceGenerateRevenueReport administratorServiceGenerateRevenueReport = new AdministratorServiceGenerateRevenueReport(paymentsRepository);
    private final AdministratorServiceViewAllBookingRecords viewAllBookingRecordsService =new AdministratorServiceViewAllBookingRecords(bookingRepository);
    private final AdministratorServiceViewAllHousekeepingRecords viewAllHousekeepingRecordsService = new AdministratorServiceViewAllHousekeepingRecords(housekeepingRepository);
    private final AdministratorServiceViewAlltheEmployeesWithTheirRole viewAllEmployeesWithRolesService= new AdministratorServiceViewAlltheEmployeesWithTheirRole(employeeRepository);
    private final AdministratorServiceViewMostBookedRoomTypes viewMostBookedRoomTypesService = new AdministratorServiceViewMostBookedRoomTypes(roomRepository);



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
        int choice = -1;

        while (choice != 11) {  // Exit option set to 11
            System.out.println("1. Add Room");
            System.out.println("2. Delete Room");
            System.out.println("3. Manage Room Status");
            System.out.println("4. Add User Account");
            System.out.println("5. View User Accounts");
            System.out.println("6. Generate Revenue Report");
            System.out.println("7. View All Booking Records");
            System.out.println("8. View All Housekeeping Records");
            System.out.println("9. View Most Booked Room Types");
            System.out.println("10. View All Employees with Their Role");
            System.out.println("11. Exit to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addRoom(scanner);
                    case 2 -> deleteRoom(scanner);
                    case 3 -> updateRoomStatus(scanner);
                    case 4 -> addUserAccount(scanner);
                    case 5 -> viewUserAccounts();
                    case 6 -> generateRevenueReport();
                    case 7 -> viewAllBookingRecords();
                    case 8 -> viewAllHousekeepingRecords();
                    case 9 -> viewMostBookedRoomTypes();
                    case 10 -> viewAllEmployeesWithRoles();
                    case 11 -> System.out.println("Returning to Main Menu...");
                    default -> System.out.println("Invalid choice. Please select a number between 1 and 11.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    private void addRoom(Scanner scanner) {
        try {
            // Collect room details
            System.out.print("Enter Hotel ID: ");
            int hotelId = Integer.parseInt(scanner.nextLine()); // Added hotel ID

            System.out.print("Enter Room Number: ");
            String roomNumber = scanner.nextLine(); // Added room number

            System.out.print("Enter Room Type (e.g., Single, Double, Suite): ");
            String roomTypeName = scanner.nextLine(); // Room type (This could be mapped to Room_Type object)

            Room_Type roomType =roomTypeRepository.getRoomTypeByName(roomTypeName); // Assume this method fetches the Room_Type object

            System.out.print("Enter Room Availability (e.g., Available, Occupied): ");
            String availabilityStatus = scanner.nextLine(); // Availability status

            System.out.print("Enter Bed Count: ");
            int bedCount = Integer.parseInt(scanner.nextLine()); // Bed count

            System.out.print("Enter Max Occupancy: ");
            int maxOccupancy = Integer.parseInt(scanner.nextLine()); // Max occupancy

            System.out.print("Enter Room Size (in sq ft): ");
            int roomSize = Integer.parseInt(scanner.nextLine()); // Room size

            System.out.print("Enter Special Amenities: ");
            String specialAmenities = scanner.nextLine(); // Special amenities
            int roomId = -1;
            // Create a Room object
            Room newRoom = new Room(
                    roomId,
                    hotelId,
                    roomNumber,
                    roomType,  // Now passing Room_Type object
                    availabilityStatus,
                    bedCount,
                    maxOccupancy,
                    roomSize,
                    specialAmenities
            );

            // Call the service to add the room
            AdministratorServiceAddRoom addRoomService = new AdministratorServiceAddRoom(roomRepository);
            String result = addRoomService.addRoom(newRoom);

            System.out.println(result);  // Print success or error message
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Error adding room: " + e.getMessage());
        }
    }
    public void deleteRoom(Scanner scanner) {
        try {
            // Ask for the room ID to be deleted
            System.out.print("Enter the Room ID to delete: ");
            int roomId = Integer.parseInt(scanner.nextLine());

            // Call the service to delete the room
            String result = administratorServiceDeleteRoom.deleteRoom(roomId);

            // Display the result of the operation
            System.out.println(result);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid room ID.");
        }
    }
    public void updateRoomStatus(Scanner scanner) {
        try {
            System.out.print("Enter the Room ID to update status: ");
            int roomId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter the new status (e.g., Available, Occupied): ");
            String newStatus = scanner.nextLine();

            // Update room status using the service
            administratorServiceManageRoomStatus.updateRoomStatus(roomId, newStatus);
            System.out.println("Room status updated successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid room ID. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error updating room status: " + e.getMessage());
        }
    }
    public void addUserAccount(Scanner scanner) {
        try {
            System.out.println("Add a New User Account");

            // Get user details
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter Phone Number: ");
            String phoneNumber = scanner.nextLine();

            System.out.print("Enter Email Address: ");
            String email = scanner.nextLine();

            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            String dateOfBirthInput = scanner.nextLine();
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthInput);

            // Add the user account
            administratorServiceAdUserAccount.addUserAccount(
                    firstName,
                    lastName,
                    phoneNumber,
                    email,
                    Date.valueOf(dateOfBirth)
            );

            System.out.println("User account successfully added.");

        } catch (IllegalArgumentException e) {
            System.out.println("Input error: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
        } catch (Exception e) {
            System.out.println("Error adding user account: " + e.getMessage());
        }
    }
    public void viewUserAccounts() {
        try {
            System.out.println("View All User Accounts");
            administratorServiceViewUserAccounts.viewUserAccounts();
        } catch (Exception e) {
            System.out.println("Error viewing user accounts: " + e.getMessage());
        }
    }
    public void generateRevenueReport() {
        try {
            System.out.println("Generating Revenue Report...");
            double totalRevenue = administratorServiceGenerateRevenueReport.generateRevenueReport();
            System.out.printf("The total revenue generated is: $%.2f%n", totalRevenue);
        } catch (SQLException e) {
            System.out.println("Error generating revenue report: " + e.getMessage());
        }
    }
    public void viewAllBookingRecords() {
        System.out.println("Viewing all booking records...");
        try {
            List<Booking> bookings = viewAllBookingRecordsService.viewAllBookingRecords();
            if (bookings.isEmpty()) {
                System.out.println("No booking records found.");
            } else {
                for (Booking booking : bookings) {
                    System.out.println(booking);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving booking records: " + e.getMessage());
        }
    }
    public void viewAllHousekeepingRecords() {
        System.out.println("Viewing all housekeeping records...");
        try {
            List<Housekeeping> housekeepingRecords = viewAllHousekeepingRecordsService.viewAllHousekeepingRecords();
            if (housekeepingRecords.isEmpty()) {
                System.out.println("No housekeeping records found.");
            } else {
                for (Housekeeping record : housekeepingRecords) {
                    System.out.println(record);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving housekeeping records: " + e.getMessage());
        }
    }
    private void receptionistMenu(Scanner scanner) {
        int choice = -1;


        while (choice != 8) {
            System.out.println("\n--- Receptionist Menu ---");
            System.out.println("1. Add New Booking");
            System.out.println("2. Modify Booking");
            System.out.println("3. Delete Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Process Payment");
            System.out.println("6. Assign Housekeeping Task");
            System.out.println("7. Housekeepers and their availability");
            System.out.println("8. Exit to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addBooking(scanner);
                    case 2 -> modifyBooking(scanner);
                    case 3 -> cancelBooking(scanner);
                    case 4 -> viewBookings(scanner);
                    case 5 -> processPayment(scanner);
                    case 6 -> assignHousekeepingTask(scanner);
                    case 7 -> viewAllHousekeepersWithAvailability();
                    case 8 -> System.out.println("Returning to Main Menu...");
                    default -> System.out.println("Invalid choice. Please select a number between 1 and 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    public void viewAllEmployeesWithRoles() {
        System.out.println("Viewing all employees with their roles...");
        try {
            Map<Users, Roles> employeesWithRoles = viewAllEmployeesWithRolesService.viewAllEmployeesWithRoles();
            if (employeesWithRoles.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                for (Map.Entry<Users, Roles> entry : employeesWithRoles.entrySet()) {
                    System.out.printf("Employee: %s, Role: %s%n", entry.getKey(), entry.getValue());
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving employees with roles: " + e.getMessage());
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
    public void assignHousekeepingTask(Scanner scanner) {
        try {
            System.out.println("Enter task date (yyyy-mm-dd): ");
            String taskDateInput = scanner.nextLine();
            Date taskDate = Date.valueOf(taskDateInput);

            System.out.println("Enter status (e.g., Pending, Completed): ");
            String status = scanner.nextLine();

            System.out.println("Enter housekeeping employee ID: ");
            int housekeepingId = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter room ID: ");
            int roomId = Integer.parseInt(scanner.nextLine());

            HousekeepingSchedule schedule = new HousekeepingSchedule(0, taskDate, status, housekeepingId, roomId);
            housekeepingScheduleService.assignHousekeepingTask(schedule);

            System.out.println("Task assigned successfully!");
        } catch (SQLException e) {
            System.err.println("Error assigning housekeeping task: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }
    private void viewMostBookedRoomTypes() {
        System.out.println("\nMost Booked Room Types:");

        List<String> mostBookedRoomTypes = viewMostBookedRoomTypesService.viewMostBookedRoomTypes();

        if (mostBookedRoomTypes.isEmpty()) {
            System.out.println("No data available.");
        } else {
            mostBookedRoomTypes.forEach(System.out::println);
        }
    }

    private void viewAllHousekeepersWithAvailability() {
        try {
            // Call the method on the instance of HousekeepingRepository
            List<Housekeeping> housekeepers = hkeeperRecAvaService.getAllHousekeepersWithAvailability();

            // Print out the details of the housekeepers (you can customize this part as needed)
            for (Housekeeping housekeeping : housekeepers) {
                System.out.println("Housekeeper ID: " + housekeeping.getEmployeeId() + ", Area: " + housekeeping.getCleaningArea() + ", Shift: " + housekeeping.getShift());
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving housekeepers: " + e.getMessage());
        }
    }


    private void housekeepingMenu(Scanner scanner) {

        HousekeepingRepository housekeepingRepository = new HousekeepingRepository(DatabaseConnection.getConnection());
        HousekeepingServiceViewPendingTasks pendingTasksService = new HousekeepingServiceViewPendingTasks(housekeepingRepository);
        HousekeepingServiceViewCompletedTasks completedTasksService = new HousekeepingServiceViewCompletedTasks(housekeepingRepository);
        HousekeepingServiceUpdateTaskStatus updateStatusService = new HousekeepingServiceUpdateTaskStatus(housekeepingRepository);
        HousekeepingServiceViewSchedule viewScheduleService = new HousekeepingServiceViewSchedule(housekeepingRepository);

        while (true) {
            System.out.println("\nHousekeeping Menu:");
            System.out.println("1. View Pending Housekeeping Tasks");
            System.out.println("2. View Completed Housekeeping Tasks");
            System.out.println("3. Update Task Status to Completed");
            System.out.println("4. View My Cleaning Schedule");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.println("Pending Housekeeping Tasks:");
                        pendingTasksService.getPendingTasks().forEach(System.out::println);
                        break;
                    case 2:
                        System.out.println("Completed Housekeeping Tasks:");
                        completedTasksService.getCompletedTasks().forEach(System.out::println);
                        break;
                    case 3:
                        System.out.print("Enter Task ID to mark as Completed: ");
                        int taskId = Integer.parseInt(scanner.nextLine());
                        updateStatusService.markTaskAsCompleted(taskId);
                        System.out.println("Task marked as Completed.");
                        break;
                    case 4:
                        System.out.print("Enter your Housekeeping Employee ID: ");
                        int housekeepingId = Integer.parseInt(scanner.nextLine());
                        System.out.println("Your Cleaning Schedule:");
                        viewScheduleService.getScheduleByEmployeeId(housekeepingId).forEach(System.out::println);
                        break;
                    case 5:
                        System.out.println("Exiting Housekeeping Menu.");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
