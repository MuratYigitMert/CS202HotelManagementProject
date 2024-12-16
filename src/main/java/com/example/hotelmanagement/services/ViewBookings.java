package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Booking;
import com.example.hotelmanagement.repository.BookingRepository;

import java.sql.SQLException;
import java.util.List;

public class ViewBookings {
    private final BookingRepository bookingRepository;

    public ViewBookings(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void viewAllBookings() {
        try {
            // Fetch all bookings from the database
            List<Booking> bookings = bookingRepository.getAllBookings();

            if (bookings.isEmpty()) {
                System.out.println("No bookings found.");
            } else {
                System.out.println("\n--- All Bookings ---");
                // Display each booking
                for (Booking booking : bookings) {
                    System.out.println("Booking ID: " + booking.getBookingId());
                    System.out.println("Guest ID: " + booking.getGuestId());
                    System.out.println("Room ID: " + booking.getRoomId());
                    System.out.println("Booking Status: " + booking.getBookingStatus());
                    System.out.println("Check-In Date: " + booking.getCheckInDate());
                    System.out.println("Check-Out Date: " + booking.getCheckOutDate());
                    System.out.println("Canceled By: " + booking.getCanceledBy());
                    System.out.println("-------------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve bookings. Details: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
