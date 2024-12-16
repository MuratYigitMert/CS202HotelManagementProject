package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Booking;
import com.example.hotelmanagement.repository.BookingRepository;

import java.sql.SQLException;
import java.util.List;

public class AdministratorServiceViewAllBookingRecords {

    private final BookingRepository bookingRepository;

    public AdministratorServiceViewAllBookingRecords(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Method to retrieve all booking records
    public List<Booking> viewAllBookingRecords() throws Exception {
        return bookingRepository.getAllBookings();
    }
}