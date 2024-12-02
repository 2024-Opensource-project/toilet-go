package com.myspring.springmaster.dataAccess.DTO;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private Long toiletId;
    private String userId;
    private int rating;
    private String reviewText;

    // Getter와 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getToiletId() {
        return toiletId;
    }

    public void setToiletId(Long toiletId) {
        this.toiletId = toiletId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}