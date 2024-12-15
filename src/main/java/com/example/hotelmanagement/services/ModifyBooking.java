package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Booking;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.GuestRepository;
import com.example.hotelmanagement.repository.RoomRepository;

import java.sql.SQLException;

public class ModifyBooking {
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;

    // Constructor for dependency injection
    public ModifyBooking(BookingRepository bookingRepository, GuestRepository guestRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
    }


    public String modifyBooking(int bookingId, int newRoomId, String newCheckInDateStr, String newCheckOutDateStr) {
        try {
            // Step 1: Find the existing booking
            Booking existingBooking = bookingRepository.findById(bookingId);
            if (existingBooking == null) {
                return "Error: Booking with ID " + bookingId + " does not exist.";
            }

            // Step 2: Validate Guest
            if (!guestRepository.existsById(existingBooking.getGuestId())) {
                return "Error: Guest with ID " + existingBooking.getGuestId() + " does not exist.";
            }

            // Step 3: Validate Room
            if (!roomRepository.existsById(newRoomId)) {
                return "Error: Room with ID " + newRoomId + " does not exist.";
            }

            // Step 4: Convert new check-in and check-out dates
            java.sql.Date newCheckInDate = java.sql.Date.valueOf(newCheckInDateStr);
            java.sql.Date newCheckOutDate = java.sql.Date.valueOf(newCheckOutDateStr);

            // Step 5: Check Room Availability (use the new `isRoomAvailableForUpdate` method for excluding current booking)
            if (!roomRepository.isRoomAvailableForModification(newRoomId, bookingId, newCheckInDate.toLocalDate(), newCheckOutDate.toLocalDate())) {
                return "Error: Room with ID " + newRoomId + " is not available for the selected dates.";
            }

            // Step 6: Modify the booking details
            existingBooking.setRoomId(newRoomId);
            existingBooking.setCheckInDate(newCheckInDate);
            existingBooking.setCheckOutDate(newCheckOutDate);
            existingBooking.setBookingStatus("Pending"); // Optional: Set status to Pending during modification

            // Step 7: Save the modified booking (call update instead of save)
            bookingRepository.updateBooking(existingBooking);
            return "Booking with ID " + bookingId + " has been successfully modified.";
        } catch (SQLException e) {
            return "Error: Unable to modify booking. Details: " + e.getMessage();
        }
    }

}
