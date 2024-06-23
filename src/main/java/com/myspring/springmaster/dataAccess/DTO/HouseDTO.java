package com.myspring.springmaster.dataAccess.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseDTO {
    private int idx;
    private String name;
    private String description;
    private String price;
    private String latitude;
    private String longitude;
    private String count;
    private String address;
    private String imageUrl;
    private String status;
    private String movingDate;
    private String submissionDate;
}
