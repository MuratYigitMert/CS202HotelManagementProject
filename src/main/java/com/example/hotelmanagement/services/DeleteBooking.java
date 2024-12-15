package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Booking;
import com.example.hotelmanagement.repository.BookingRepository;

import java.sql.SQLException;

public class DeleteBooking {
    private final BookingRepository bookingRepository;

    // Constructor for dependency injection
    public DeleteBooking(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public String deleteBooking(int bookingId) {
        try {
            // Step 1: Find the existing booking
            Booking existingBooking = bookingRepository.findById(bookingId);
            if (existingBooking == null) {
                return "Error: Booking with ID " + bookingId + " does not exist.";
            }

            // Step 2: Delete the booking
            bookingRepository.deleteBooking(bookingId);
            return "Booking with ID " + bookingId + " has been successfully deleted.";

        } catch (SQLException e) {
            return "Error: Unable to delete booking. Details: " + e.getMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
