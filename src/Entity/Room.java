package Entity;

public class Room {
    // Fields corresponding to the Room table columns
    private int roomId;               // Primary key, Auto-incremented
    private int hotelId;              // Foreign key referencing Hotel
    private String roomNumber;        // Unique room number
    private int roomTypeId;           // Foreign key referencing Room_Type
    private String roomType;          // Room type description (may be redundant, you can use the Room_Type table directly)
    private double price;             // Price of the room
    private String availabilityStatus; // Availability status: Available, Occupied, Under Maintenance
    private int bedCount;             // Number of beds in the room
    private int maxOccupancy;         // Maximum number of people the room can accommodate
    private int roomSize;             // Size of the room in square feet or meters
    private String specialAmenities;  // Special amenities available in the room

    // Constructor
    public Room(int roomId, int hotelId, String roomNumber, int roomTypeId, String roomType,
                double price, String availabilityStatus, int bedCount, int maxOccupancy,
                int roomSize, String specialAmenities) {
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.roomTypeId = roomTypeId;
        this.roomType = roomType;
        this.price = price;
        this.availabilityStatus = availabilityStatus;
        this.bedCount = bedCount;
        this.maxOccupancy = maxOccupancy;
        this.roomSize = roomSize;
        this.specialAmenities = specialAmenities;
    }

    // Getters and Setters in your preferred format

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public int getRoomTypeId() { return roomTypeId; }
    public void setRoomTypeId(int roomTypeId) { this.roomTypeId = roomTypeId; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }

    public int getBedCount() { return bedCount; }
    public void setBedCount(int bedCount) { this.bedCount = bedCount; }

    public int getMaxOccupancy() { return maxOccupancy; }
    public void setMaxOccupancy(int maxOccupancy) { this.maxOccupancy = maxOccupancy; }

    public int getRoomSize() { return roomSize; }
    public void setRoomSize(int roomSize) { this.roomSize = roomSize; }

    public String getSpecialAmenities() { return specialAmenities; }
    public void setSpecialAmenities(String specialAmenities) { this.specialAmenities = specialAmenities; }

    // Optionally, override toString() for easy debugging/logging
    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", hotelId=" + hotelId +
                ", roomNumber='" + roomNumber + '\'' +
                ", roomTypeId=" + roomTypeId +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                ", availabilityStatus='" + availabilityStatus + '\'' +
                ", bedCount=" + bedCount +
                ", maxOccupancy=" + maxOccupancy +
                ", roomSize=" + roomSize +
                ", specialAmenities='" + specialAmenities + '\'' +
                '}';
    }
}
