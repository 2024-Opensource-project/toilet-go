package com.myspring.springmaster.dataAccess.DTO;

import com.myspring.springmaster.dataAccess.entity.House;
import com.myspring.springmaster.dataAccess.entity.HouseDetail;
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
    private HouseDTO house;
    private String type;
    private String size;
    private int supplyCount;
    private int deposit;
    private int monthlyRent;
    private String imageUrl;

    public HouseDetail toEntity(House houseEntity){
        HouseDetail houseDetail = HouseDetail.builder()
                .id(id)
                .house(houseEntity)
                .type(type)
                .size(size)
                .supplyCount(supplyCount)
                .deposit(deposit)
                .monthlyRent(monthlyRent)
                .imageUrl(imageUrl)
                .build();
        return houseDetail;
    }
}
