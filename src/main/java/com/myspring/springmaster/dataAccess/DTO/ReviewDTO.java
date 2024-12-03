package com.myspring.springmaster.dataAccess.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ReviewDTO {
    // Getter와 Setter
    private Long id;
    private Long toiletId;
    private String userId;
    private int rating;
    private String reviewText;

}