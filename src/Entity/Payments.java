package Entity;

import java.sql.Date;

public class Payments {
    // Fields corresponding to the Payments table columns
    private int paymentId;       // PaymentID
    private String paymentStatus; // PaymentStatus (Paid, Unpaid, Pending, Failed, Partially Paid)
    private double amount;        // Amount
    private Date paymentDate;     // PaymentDate
    private int bookingId;        // Foreign key from Booking (BookingID)

    // Constructor
    public Payments(int paymentId, String paymentStatus, double amount, Date paymentDate, int bookingId) {
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.bookingId = bookingId;
    }

    // Getters and Setters
    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    // Optionally, override toString() for easy debugging/logging
    @Override
    public String toString() {
        return "Payments{" +
                "paymentId=" + paymentId +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", bookingId=" + bookingId +
                '}';
    }
}
