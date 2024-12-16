package com.example.hotelmanagement.Entity;

import java.sql.Date;

public class HousekeepingSchedule {
    // Fields corresponding to the Housekeeping_Schedule table columns
    private int taskId;           // TaskID - Primary Key
    private Date taskDate;        // TaskDate
    private String status;        // Status
    private int housekeepingId;   // Housekeeping_ID - Foreign Key
    private int roomId;           // Room_ID - Foreign Key

    // Constructor
    public HousekeepingSchedule(int taskId, Date taskDate, String status, int housekeepingId, int roomId) {
        this.taskId = taskId;
        this.taskDate = taskDate;
        this.status = status;
        this.housekeepingId = housekeepingId;
        this.roomId = roomId;
    }

    // Getters and Setters
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHousekeepingId() {
        return housekeepingId;
    }

    public void setHousekeepingId(int housekeepingId) {
        this.housekeepingId = housekeepingId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    // Optionally, override toString() for debugging/logging
    @Override
    public String toString() {
        return "HousekeepingSchedule{" +
                "taskId=" + taskId +
                ", taskDate=" + taskDate +
                ", status='" + status + '\'' +
                ", housekeepingId=" + housekeepingId +
                ", roomId=" + roomId +
                '}';
    }
}
