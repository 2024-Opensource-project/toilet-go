package com.myspring.springmaster.dataAccess.DTO;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HouseDTO {

    private int id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String status;
    private Date moveInDate;
    private Date applyStartDate;
    private Date applyEndDate;
    private List<HouseDetailDTO> houseDetails;

    // Getters and setters
}
