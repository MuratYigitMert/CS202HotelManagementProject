package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Booking;
import com.example.hotelmanagement.repository.BookingRepository;

import java.util.List;

public class ViewMyBookings {
    private final BookingRepository bookingRepository;

    public ViewMyBookings(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public String getBookingsByGuestId(int guestId) {
        List<Booking> bookings = bookingRepository.findBookingsByGuestId(guestId);

        if (bookings.isEmpty()) {
            return "No bookings found for Guest ID: " + guestId;
        }

        StringBuilder result = new StringBuilder("Bookings for Guest ID " + guestId + ":\n");
        for (Booking booking : bookings) {
            result.append("Booking ID: ").append(booking.getBookingId())
                    .append(", Room ID: ").append(booking.getRoomId())
                    .append(", Check-in Date: ").append(booking.getCheckInDate())
                    .append(", Check-out Date: ").append(booking.getCheckOutDate())
                    .append(", Status: ").append(booking.getBookingStatus())
                    .append("\n");
        }
        return result.toString();
    }
}
