package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Booking;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.GuestRepository;
import com.example.hotelmanagement.repository.RoomRepository;

import java.sql.SQLException;

public class AddBooking {
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;

    // Constructor for dependency injection
    public AddBooking(BookingRepository bookingRepository, GuestRepository guestRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
    }

    public String addBooking(Booking booking) {
        try {
            // Step 1: Validate Guest
            if (!guestRepository.existsById(booking.getGuestId())) {
                return "Error: Guest with ID " + booking.getGuestId() + " does not exist.";
            }

            // Step 2: Validate Room
            if (!roomRepository.existsById(booking.getRoomId())) {
                return "Error: Room with ID " + booking.getRoomId() + " does not exist.";
            }

            // Step 3: Check Room Availability
            if (!roomRepository.isRoomAvailable(booking.getRoomId(), booking.getCheckInDate().toLocalDate(), booking.getCheckOutDate().toLocalDate())) {
                return "Error: Room with ID " + booking.getRoomId() + " is not available for the selected dates.";
            }
            booking.setBookingStatus("Pending");
            booking.setCanceledBy(0);
            // Step 4: Insert Booking
            bookingRepository.save(booking);
            return "Booking added successfully!";
        } catch (SQLException e) {
            return "Error: Unable to add booking. Details: " + e.getMessage();
        }
    }
}
