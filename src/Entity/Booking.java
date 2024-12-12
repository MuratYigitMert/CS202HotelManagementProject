package Entity;

import java.sql.Date;

public class Booking {
    // Fields corresponding to the Booking table columns
    private int bookingId;        // BookingID
    private String bookingStatus; // BookingStatus (Confirmed, Pending, Canceled)
    private Date checkInDate;     // CheckInDate
    private Date checkOutDate;    // CheckOutDate
    private int guestId;          // Foreign key from Guest (guest_id)
    private int roomId;           // Foreign key from Room (room_id)
    private int canceledBy;       // ID of the user who canceled the booking

    // Constructor
    public Booking(int bookingId, String bookingStatus, Date checkInDate, Date checkOutDate, int guestId, int roomId, int canceledBy) {
        this.bookingId = bookingId;
        this.bookingStatus = bookingStatus;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guestId = guestId;
        this.roomId = roomId;
        this.canceledBy = canceledBy;
    }

    // Getters and Setters in your preferred format
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }

    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }

    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }

    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public int getCanceledBy() { return canceledBy; }
    public void setCanceledBy(int canceledBy) { this.canceledBy = canceledBy; }

    // Optionally, override toString() for easy debugging/logging
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", bookingStatus='" + bookingStatus + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", guestId=" + guestId +
                ", roomId=" + roomId +
                ", canceledBy=" + canceledBy +
                '}';
    }
}
