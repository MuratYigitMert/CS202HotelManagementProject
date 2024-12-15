package com.example.hotelmanagement.Entity;

public class Room {
    private int roomId;
    private int hotelId;
    private String roomNumber;
    private String availabilityStatus;
    private int roomTypeId;

    private int bedCount;
    private int maxOccupancy;
    private int roomSize;
    private String specialAmenities;
    private String roomType; // Added
    private double price;    // Added

    // Constructor
    public Room(int roomId, int hotelId, String roomNumber, int roomTypeId,
                String availabilityStatus, int bedCount, int maxOccupancy,
                int roomSize, String specialAmenities, String roomType, double price) {
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.roomTypeId = roomTypeId;
        this.availabilityStatus = availabilityStatus;
        this.bedCount = bedCount;
        this.maxOccupancy = maxOccupancy;
        this.roomSize = roomSize;
        this.specialAmenities = specialAmenities;
        this.roomType = roomType;
        this.price = price;
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

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

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
