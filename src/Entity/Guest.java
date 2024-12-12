package Entity;

public class Guest {
    // Fields corresponding to the Guest table columns
    private int guestId;           // Primary key
    private int userId;            // Foreign key referencing Users table
    private String identificationNumber; // Guest's identification number (e.g., passport, ID card)

    // Constructor
    public Guest(int guestId, int userId, String identificationNumber) {
        this.guestId = guestId;
        this.userId = userId;
        this.identificationNumber = identificationNumber;
    }

    // Getters and Setters in your preferred format

    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getIdentificationNumber() { return identificationNumber; }
    public void setIdentificationNumber(String identificationNumber) { this.identificationNumber = identificationNumber; }

    // Optionally, override toString() for easy debugging/logging
    @Override
    public String toString() {
        return "Guest{" +
                "guestId=" + guestId +
                ", userId=" + userId +
                ", identificationNumber='" + identificationNumber + '\'' +
                '}';
    }
}
