package com.myspring.springmaster.dataAccess.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HouseDetailDTO {

    private int id;
    private int houseId;
    private String type;
    private String size;
    private int supplyCount;
    private int deposit;
    private int monthlyRent;
    private String imageUrl;
}
