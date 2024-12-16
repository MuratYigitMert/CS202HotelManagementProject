package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Booking;
import com.example.hotelmanagement.repository.BookingRepository;

import java.util.List;
import java.util.Scanner;

public class CancelBooking {
    private final BookingRepository bookingRepository;

    public CancelBooking(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public String cancelBookingByGuestId(int guestId, Scanner scanner) {
        // Fetch bookings for the guest
        List<Booking> bookings = bookingRepository.findBookingsByGuestId(guestId);

        if (bookings.isEmpty()) {
            return "No bookings found for Guest ID: " + guestId;
        }

        // Display bookings for the guest
        System.out.println("Bookings for Guest ID " + guestId + ":");
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            System.out.printf("[%d] Booking ID: %d, Room ID: %d, Check-in: %s, Check-out: %s, Status: %s%n",
                    i + 1, booking.getBookingId(), booking.getRoomId(),
                    booking.getCheckInDate(), booking.getCheckOutDate(), booking.getBookingStatus());
        }

        // Ask the guest to choose a booking to delete
        System.out.print("Enter the number corresponding to the booking you want to cancel: ");
        int bookingIndex = Integer.parseInt(scanner.nextLine()) - 1;

        // Validate the choice
        if (bookingIndex < 0 || bookingIndex >= bookings.size()) {
            return "Invalid choice. No booking canceled.";
        }

        // Delete the selected booking
        int bookingId = bookings.get(bookingIndex).getBookingId();
        boolean isDeleted = bookingRepository.deleteBookingById(bookingId);

        if (isDeleted) {
            return "Booking ID " + bookingId + " has been successfully canceled.";
        } else {
            return "Failed to cancel Booking ID " + bookingId + ". Please try again.";
        }
    }
}
