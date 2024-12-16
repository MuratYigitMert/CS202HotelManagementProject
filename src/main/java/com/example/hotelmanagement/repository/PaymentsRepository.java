package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.Entity.Payments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentsRepository {
    private final Connection connection;

    public PaymentsRepository(Connection connection) {
        this.connection = connection;
    }

    // Fetch unpaid payments by Guest ID
    public List<Payments> findUnpaidPaymentsByGuestId(int guestId) throws SQLException {
        String query = """
            SELECT p.* FROM payments p
            JOIN booking b ON p.booking_id = b.bookingId
            WHERE b.guest_id = ? AND p.paymentStatus != 'Paid'
        """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, guestId);
            ResultSet resultSet = statement.executeQuery();

            List<Payments> payments = new ArrayList<>();
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
            return payments;
        }
    }

    // Update payment status
    public boolean updatePaymentStatus(int paymentId, String newStatus) throws SQLException {
        String query = "UPDATE payments SET paymentStatus = ? WHERE paymentId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newStatus);
            statement.setInt(2, paymentId);
            return statement.executeUpdate() > 0;
        }
    }

    // Fetch all payments with "Paid" status
    public List<Payments> findAllPaidPayments() throws SQLException {
        String query = "SELECT * FROM payments WHERE paymentStatus = 'Paid'";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            List<Payments> payments = new ArrayList<>();
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
            return payments;
        }
    }

}
