package com.example.hotelmanagement.Entity;

import java.sql.Date;

public class Reviews {
    private int reviewId;        // review_id
    private int roomId;          // room_id (foreign key)
    private int guestId;         // guest_id (foreign key)
    private String reviewText;   // review_text
    private int rating;          // rating
    private Date reviewDate;     // review_date

    // Constructor
    public Reviews(int reviewId, int roomId, int guestId, String reviewText, int rating, Date reviewDate) {
        this.reviewId = reviewId;
        this.roomId = roomId;
        this.guestId = guestId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }

    // Getters and Setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    // Optionally override toString() for easy debugging/logging
    @Override
    public String toString() {
        return "Reviews{" +
                "reviewId=" + reviewId +
                ", roomId=" + roomId +
                ", guestId=" + guestId +
                ", reviewText='" + reviewText + '\'' +
                ", rating=" + rating +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
