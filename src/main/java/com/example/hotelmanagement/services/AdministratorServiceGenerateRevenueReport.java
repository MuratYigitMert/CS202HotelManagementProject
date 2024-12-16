package com.example.hotelmanagement.services;

import com.example.hotelmanagement.repository.PaymentsRepository;
import com.example.hotelmanagement.Entity.Payments;

import java.sql.SQLException;
import java.util.List;

public class AdministratorServiceGenerateRevenueReport {

    private final PaymentsRepository paymentsRepository;

    public AdministratorServiceGenerateRevenueReport(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    /**
     * Generate a revenue report based on completed payments.
     *
     * @return Total revenue generated
     * @throws SQLException If a database error occurs
     */
    public double generateRevenueReport() throws SQLException {
        double totalRevenue = 0.0;

        // Query to find all completed payments
        List<Payments> completedPayments = paymentsRepository.findAllPaidPayments();

        // Calculate the total revenue
        for (Payments payment : completedPayments) {
            totalRevenue += payment.getAmount();
        }

        System.out.println("Total Revenue: $" + totalRevenue);
        return totalRevenue;
    }
}
