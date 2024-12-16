package com.example.hotelmanagement.services;

import com.example.hotelmanagement.Entity.Payments;
import com.example.hotelmanagement.repository.PaymentsRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProcessPayment {
    private final PaymentsRepository paymentsRepository;

    public ProcessPayment(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    public String processPayment(int guestId, Scanner scanner) {
        try {
            // Fetch unpaid payments for the guest
            List<Payments> unpaidPayments = paymentsRepository.findUnpaidPaymentsByGuestId(guestId);

            if (unpaidPayments.isEmpty()) {
                return "No unpaid payments found for Guest ID: " + guestId;
            }

            // Display unpaid payments
            System.out.println("Unpaid Payments:");
            for (Payments payment : unpaidPayments) {
                System.out.println("Payment ID: " + payment.getPaymentId() +
                        ", Booking ID: " + payment.getBookingId() +
                        ", Status: " + payment.getPaymentStatus() +
                        ", Amount Due: $" + payment.getAmount());
            }

            // Ask the receptionist to select a payment for processing
            System.out.print("Enter the Payment ID to process: ");
            int paymentId = Integer.parseInt(scanner.nextLine());

            // Validate if the Payment ID is valid and unpaid
            Payments selectedPayment = unpaidPayments.stream()
                    .filter(payment -> payment.getPaymentId() == paymentId)
                    .findFirst()
                    .orElse(null);

            if (selectedPayment == null) {
                return "Invalid Payment ID or the payment is already settled.";
            }

            // Process the payment
            System.out.print("Enter the payment amount: ");
            double paymentAmount = Double.parseDouble(scanner.nextLine());

            if (paymentAmount < selectedPayment.getAmount()) {
                return "Payment amount is less than the amount due. Payment failed.";
            }

            // Update the payment status to "Paid"
            boolean isUpdated = paymentsRepository.updatePaymentStatus(paymentId, "Paid");
            if (isUpdated) {
                return "Payment processed successfully for Payment ID: " + paymentId;
            } else {
                return "Error processing payment. Please try again.";
            }

        } catch (SQLException e) {
            return "Error accessing the database: " + e.getMessage();
        } catch (NumberFormatException e) {
            return "Invalid input. Please enter a valid number.";
        }
    }
}
