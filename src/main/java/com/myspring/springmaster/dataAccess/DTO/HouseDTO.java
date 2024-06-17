package com.myspring.springmaster.dataAccess.DTO;

import lombok.Data;

@Data
public class HouseDTO {
    private int idx;
    private String name;
    private String description;
    private long price;
    private int count;
    private String address;
    private String imageUrl;
    private String status;
    private String movingDate;
    private String subMissionDate;
}
