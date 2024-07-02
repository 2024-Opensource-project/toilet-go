package com.myspring.springmaster.dataAccess.DTO;

import com.myspring.springmaster.dataAccess.entity.House;
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
    private House house;
    private String type;
    private String size;
    private int supplyCount;
    private int deposit;
    private int monthlyRent;
    private String imageUrl;
}
