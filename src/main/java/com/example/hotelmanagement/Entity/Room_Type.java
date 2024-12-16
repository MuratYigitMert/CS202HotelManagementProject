package com.example.hotelmanagement.Entity;

import java.math.BigDecimal;

public class Room_Type {
    private int roomTypeId;       // room_type_id
    private String typeName;      // type_name
    private double defaultPrice; // default_price

    // Constructor
    public Room_Type(int roomTypeId, String typeName, double defaultPrice) {
        this.roomTypeId = roomTypeId;
        this.typeName = typeName;
        this.defaultPrice = defaultPrice;
    }

    // Getters and Setters
    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    // Optionally override toString() for easy debugging/logging
    @Override
    public String toString() {
        return "RoomType{" +
                "roomTypeId=" + roomTypeId +
                ", typeName='" + typeName + '\'' +
                ", defaultPrice=" + defaultPrice +
                '}';
    }
}
