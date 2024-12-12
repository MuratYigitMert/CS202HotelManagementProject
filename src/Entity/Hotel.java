package Entity;

public class Hotel {
    // Fields corresponding to the Hotel table columns
    private int hotelId;              // Primary key, Auto-incremented
    private String hotelName;         // Name of the hotel
    private String hotelAddress;      // Address of the hotel
    private String hotelCity;         // City where the hotel is located
    private String phoneNumber;       // Contact phone number
    private String email;             // Contact email address

    // Constructor
    public Hotel(int hotelId, String hotelName, String hotelAddress, String hotelCity, String phoneNumber, String email) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelCity = hotelCity;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters for each field

    public int getHotelId() {return hotelId;}

    public void setHotelId(int hotelId) { this.hotelId = hotelId;}

    public String getHotelName() {return hotelName;}

    public void setHotelName(String hotelName) {this.hotelName = hotelName;}

    public String getHotelAddress() {return hotelAddress;}

    public void setHotelAddress(String hotelAddress) {this.hotelAddress = hotelAddress;}

    public String getHotelCity() {return hotelCity;}

    public void setHotelCity(String hotelCity) {this.hotelCity = hotelCity;}

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    // Optionally, you can override the toString method for easy debugging/logging
    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", hotelName='" + hotelName + '\'' +
                ", hotelAddress='" + hotelAddress + '\'' +
                ", hotelCity='" + hotelCity + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
