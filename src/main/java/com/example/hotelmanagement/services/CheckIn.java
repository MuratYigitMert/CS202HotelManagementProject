package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Booking;
import com.example.hotelmanagement.Entity.Room;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.RoomRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class CheckIn {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public CheckIn(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    public String checkInBooking(int bookingId) throws SQLException {
        // Retrieve the booking details
        Booking booking = bookingRepository.findById(bookingId);
        if (booking == null) {
            return "Error: No booking found with ID " + bookingId;
        }

        // Ensure the booking status is confirmed or pending
        if (!booking.getBookingStatus().equals("Confirmed") && !booking.getBookingStatus().equals("Pending")) {
            return "Error: Booking is not in a valid status for check-in.";
        }

        // Ensure the check-in date is today or in the future
        Date currentDate = new Date(System.currentTimeMillis());
        if (booking.getCheckInDate().before(currentDate)) {
            return "Error: Check-in date is in the past.";
        }

        // Update the booking status to Confirmed
        booking.setBookingStatus("Confirmed");
        bookingRepository.updateBookingStatus(booking);

        // Update the room availability status to Occupied
        Room room = roomRepository.getRoomById(booking.getRoomId());
        if (room != null) {
            room.setAvailabilityStatus("Occupied");
            roomRepository.updateRoomStatus(room);
        } else {
            return "Error: Room not found for the booking.";
        }

        return "Check-In successful! Booking ID: " + bookingId + " has been checked in.";
    }

    public void handleCheckIn(Scanner scanner) {
        System.out.print("Enter the Booking ID to check in: ");
        int bookingId = Integer.parseInt(scanner.nextLine());

        try {
            String result = checkInBooking(bookingId);
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println("Error: Unable to check in. Details: " + e.getMessage());
        }
    }
}
