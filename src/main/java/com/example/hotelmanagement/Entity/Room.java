package com.example.hotelmanagement.Entity;

public class Room {
    private int roomId;
    private int hotelId;
    private String roomNumber;
    private String availabilityStatus;
    private Room_Type roomType;  // Use RoomType instead of roomTypeId

    private int bedCount;
    private int maxOccupancy;
    private int roomSize;
    private String specialAmenities;

    // Constructor
    public Room(int roomId, int hotelId, String roomNumber, Room_Type roomType,
                String availabilityStatus, int bedCount, int maxOccupancy,
                int roomSize, String specialAmenities) {
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;  // Now referring to the RoomType object
        this.availabilityStatus = availabilityStatus;
        this.bedCount = bedCount;
        this.maxOccupancy = maxOccupancy;
        this.roomSize = roomSize;
        this.specialAmenities = specialAmenities;
    }

    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Room_Type getRoomType() {
        return roomType;  // Get the RoomType object
    }

    public void setRoomType(Room_Type roomType) {
        this.roomType = roomType;  // Set the RoomType object
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public int getBedCount() {
        return bedCount;
    }

    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public String getSpecialAmenities() {
        return specialAmenities;
    }

    public void setSpecialAmenities(String specialAmenities) {
        this.specialAmenities = specialAmenities;
    }
}
