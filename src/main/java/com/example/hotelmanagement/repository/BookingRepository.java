package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
public class BookingRepository {
    private final Connection connection;
    public BookingRepository(Connection connection) {
        this.connection = connection;
    }
    // CREATE
    final String INSERT_BOOKING = "INSERT INTO Booking ( Booking_status, checkInDate, checkOutDate, guest_Id, room_Id, canceled_By) VALUES (?, ?, ?, ?, ?, ?)";

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

    public void updateBookingStatus(Booking booking) throws SQLException {
        String query = "UPDATE Booking SET Booking_status = ? WHERE BookingID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, booking.getBookingStatus());
            statement.setInt(2, booking.getBookingId());
            statement.executeUpdate();
        }
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
    public void updateBooking(Booking booking) throws SQLException {
        String query = "UPDATE Booking SET Room_ID = ?, CheckInDate = ?, CheckOutDate = ?, Booking_status = ? WHERE BookingID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, booking.getRoomId());
            stmt.setDate(2, booking.getCheckInDate());
            stmt.setDate(3, booking.getCheckOutDate());
            stmt.setString(4, booking.getBookingStatus());
            stmt.setInt(5, booking.getBookingId());
            stmt.executeUpdate();
        }
    }


    // DELETE BY ID
    public void deleteBooking(int bookingId) throws SQLException {
        String query = "DELETE FROM Booking WHERE BookingID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No booking found with ID " + bookingId);
            }
        }
    }


    // DELETE ALL
    public void deleteAllBookings() throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL)) {
            statement.executeUpdate();
        }
    }


    public void save(Booking booking) throws SQLException {
        // SQL query to insert a booking with all necessary fields
        String query = "INSERT INTO Booking (Booking_status, guest_id, room_id, CheckInDate, CheckOutDate, canceled_by) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, booking.getBookingStatus()); // Booking status
            stmt.setInt(2, booking.getGuestId());          // Guest ID
            stmt.setInt(3, booking.getRoomId());           // Room ID
            stmt.setDate(4, booking.getCheckInDate());     // Check-in date
            stmt.setDate(5, booking.getCheckOutDate());    // Check-out date
            stmt.setInt(6, booking.getCanceledBy());       // Canceled by
            stmt.executeUpdate();
        }
    }
    public Booking findById(int bookingId) throws SQLException {
        String query = "SELECT * FROM Booking WHERE BookingID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookingId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Booking(
                            resultSet.getInt("BookingID"),
                            resultSet.getString("Booking_status"),
                            resultSet.getDate("CheckInDate"),
                            resultSet.getDate("CheckOutDate"),
                            resultSet.getInt("Guest_ID"),
                            resultSet.getInt("Room_ID"),
                            resultSet.getInt("canceled_by")
                    );
                } else {
                    return null; // No booking found with the given ID
                }
            }
        }
    }


}
