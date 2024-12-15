package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class BookingRepository {
    // CREATE
    final String INSERT_BOOKING = "INSERT INTO Booking (bookingId, booking_Status, checkInDate, checkOutDate, guest_Id, room_Id, canceled_By) VALUES (?, ?, ?, ?, ?, ?, ?)";

    // READ
    final String SELECT_ALL_BOOKINGS = "SELECT * FROM Booking";
    final String SELECT_BOOKING_BY_bookingId = "SELECT * FROM Booking WHERE bookingId = ?";
    final String SELECT_BOOKING_BY_guestId = "SELECT * FROM Booking WHERE Guest_ID = ?";
    final String SELECT_BOOKING_BY_roomId = "SELECT * FROM Booking WHERE Room_ID = ?";

    // UPDATE
    final String UPDATE_BOOKING = "UPDATE Booking SET Booking_status = ?, CheckInDate = ?, CheckOutDate = ?, Guest_ID = ?, Room_ID = ?, canceled_by = ? WHERE BookingID = ?";

    // DELETE
    final String DELETE_BOOKING = "DELETE FROM Booking WHERE BookingID = ?";
    final String DELETE_ALL = "DELETE FROM Booking";

    private final Connection connection;

    public BookingRepository(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void insertBooking(Booking booking) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_BOOKING)) {
            statement.setInt(1, booking.getBookingId());
            statement.setString(2, booking.getBookingStatus());
            statement.setDate(3, booking.getCheckInDate());
            statement.setDate(4, booking.getCheckOutDate());
            statement.setInt(5, booking.getGuestId());
            statement.setInt(6, booking.getRoomId());
            statement.setInt(7, booking.getCanceledBy());
            statement.executeUpdate();
        }
    }

    // READ ALL
    public List<Booking> getAllBookings() throws Exception {
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKINGS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Booking booking = new Booking(
                        resultSet.getInt("bookingId"),
                        resultSet.getString("bookingStatus"),
                        resultSet.getDate("checkInDate"),
                        resultSet.getDate("checkOutDate"),
                        resultSet.getInt("guestId"),
                        resultSet.getInt("roomId"),
                        resultSet.getInt("canceledBy")
                );
                bookings.add(booking);
            }
        }
        return bookings;
    }

    // READ BY ID
    public Booking getBookingById(int bookingId) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BOOKING_BY_bookingId)) {
            statement.setInt(1, bookingId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Booking(
                            resultSet.getInt("bookingId"),
                            resultSet.getString("bookingStatus"),
                            resultSet.getDate("checkInDate"),
                            resultSet.getDate("checkOutDate"),
                            resultSet.getInt("guestId"),
                            resultSet.getInt("roomId"),
                            resultSet.getInt("canceledBy")
                    );
                }
            }
        }
        return null;
    }

    // READ BY GUEST ID
    public List<Booking> getBookingsByGuestId(int guestId) throws Exception {
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BOOKING_BY_guestId)) {
            statement.setInt(1, guestId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Booking booking = new Booking(
                            resultSet.getInt("bookingId"),
                            resultSet.getString("bookingStatus"),
                            resultSet.getDate("checkInDate"),
                            resultSet.getDate("checkOutDate"),
                            resultSet.getInt("guestId"),
                            resultSet.getInt("roomId"),
                            resultSet.getInt("canceledBy")
                    );
                    bookings.add(booking);
                }
            }
        }
        return bookings;
    }

    // READ BY ROOM ID
    public List<Booking> getBookingsByRoomId(int roomId) throws Exception {
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BOOKING_BY_roomId)) {
            statement.setInt(1, roomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Booking booking = new Booking(
                            resultSet.getInt("bookingId"),
                            resultSet.getString("bookingStatus"),
                            resultSet.getDate("checkInDate"),
                            resultSet.getDate("checkOutDate"),
                            resultSet.getInt("guestId"),
                            resultSet.getInt("roomId"),
                            resultSet.getInt("canceledBy")
                    );
                    bookings.add(booking);
                }
            }
        }
        return bookings;
    }

    // UPDATE
    public void updateBooking(Booking booking) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BOOKING)) {
            statement.setString(1, booking.getBookingStatus());
            statement.setDate(2, booking.getCheckInDate());
            statement.setDate(3, booking.getCheckOutDate());
            statement.setInt(4, booking.getGuestId());
            statement.setInt(5, booking.getRoomId());
            statement.setInt(6, booking.getCanceledBy());
            statement.setInt(7, booking.getBookingId());
            statement.executeUpdate();
        }
    }

    // DELETE BY ID
    public void deleteBooking(int bookingId) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BOOKING)) {
            statement.setInt(1, bookingId);
            statement.executeUpdate();
        }
    }

    // DELETE ALL
    public void deleteAllBookings() throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL)) {
            statement.executeUpdate();
        }
    }


}
