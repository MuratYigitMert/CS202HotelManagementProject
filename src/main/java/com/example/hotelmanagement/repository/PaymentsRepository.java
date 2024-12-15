package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Payments;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentsRepository {
    // CREATE
    final String INSERT_PAYMENT = "INSERT INTO Payments (PaymentID, PaymentStatus, Amount, PaymentDate, Booking_ID) VALUES (?, ?, ?, ?, ?)";

    // READ
    final String SELECT_ALL_PAYMENTS = "SELECT * FROM Payments";
    final String SELECT_PAYMENT_BY_PaymentID = "SELECT * FROM Payments WHERE PaymentID = ?";
    final String SELECT_PAYMENT_BY_BookingID = "SELECT * FROM Payments WHERE Booking_ID = ?";

    // UPDATE
    final String UPDATE_PAYMENT = "UPDATE Payments SET PaymentStatus = ?, Amount = ?, PaymentDate = ?, Booking_ID = ? WHERE PaymentID = ?";

    // DELETE
    final String DELETE_PAYMENT = "DELETE FROM Payments WHERE PaymentID = ?";
    final String DELETE_ALL = "DELETE FROM Payments";

    // Connection to the database
    private final Connection connection;

    // Constructor to initialize the connection
    public PaymentsRepository(Connection connection) {
        this.connection = connection;
    }

    // CREATE: Insert a payment into the database
    public void insertPayment(Payments payment) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PAYMENT)) {
            statement.setInt(1, payment.getPaymentId());
            statement.setString(2, payment.getPaymentStatus());
            statement.setDouble(3, payment.getAmount());
            statement.setDate(4, payment.getPaymentDate());
            statement.setInt(5, payment.getBookingId());
            statement.executeUpdate();
        }
    }

    // READ ALL: Get all payments from the database
    public List<Payments> getAllPayments() throws SQLException {
        List<Payments> payments = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PAYMENTS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Payments payment = new Payments(
                        resultSet.getInt("PaymentID"),
                        resultSet.getString("PaymentStatus"),
                        resultSet.getDouble("Amount"),
                        resultSet.getDate("PaymentDate"),
                        resultSet.getInt("Booking_ID")
                );
                payments.add(payment);
            }
        }
        return payments;
    }

    // READ BY PAYMENT ID: Get a payment by PaymentID
    public Payments getPaymentById(int paymentId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PAYMENT_BY_PaymentID)) {
            statement.setInt(1, paymentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Payments(
                            resultSet.getInt("PaymentID"),
                            resultSet.getString("PaymentStatus"),
                            resultSet.getDouble("Amount"),
                            resultSet.getDate("PaymentDate"),
                            resultSet.getInt("Booking_ID")
                    );
                }
            }
        }
        return null;
    }

    // READ BY BOOKING ID: Get payments by BookingID
    public List<Payments> getPaymentsByBookingId(int bookingId) throws SQLException {
        List<Payments> payments = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PAYMENT_BY_BookingID)) {
            statement.setInt(1, bookingId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Payments payment = new Payments(
                            resultSet.getInt("PaymentID"),
                            resultSet.getString("PaymentStatus"),
                            resultSet.getDouble("Amount"),
                            resultSet.getDate("PaymentDate"),
                            resultSet.getInt("Booking_ID")
                    );
                    payments.add(payment);
                }
            }
        }
        return payments;
    }

    // UPDATE: Update a payment's information in the database
    public void updatePayment(Payments payment) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PAYMENT)) {
            statement.setString(1, payment.getPaymentStatus());
            statement.setDouble(2, payment.getAmount());
            statement.setDate(3, payment.getPaymentDate());
            statement.setInt(4, payment.getBookingId());
            statement.setInt(5, payment.getPaymentId());
            statement.executeUpdate();
        }
    }

    // DELETE: Delete a payment from the database by PaymentID
    public void deletePayment(int paymentId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PAYMENT)) {
            statement.setInt(1, paymentId);
            statement.executeUpdate();
        }
    }

    // DELETE ALL: Delete all payments from the database
    public void deleteAllPayments() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL)) {
            statement.executeUpdate();
        }
    }
}
