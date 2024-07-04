package com.myspring.springmaster.dataAccess.DTO;

import com.myspring.springmaster.dataAccess.entity.House;
import com.myspring.springmaster.dataAccess.entity.HouseDetail;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
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

    public House toEntity(){
        List<HouseDetail> list = new ArrayList<>();
        House.HouseBuilder houseBuilder = House.builder();
        houseBuilder.id(id);
        houseBuilder.name(name);
        houseBuilder.address(address);
        houseBuilder.latitude(latitude);
        houseBuilder.longitude(longitude);
        houseBuilder.status(status);
        houseBuilder.moveInDate(moveInDate);
        houseBuilder.applyStartDate(applyStartDate);
        houseBuilder.applyEndDate(applyEndDate);

        House house = houseBuilder.build();
        for(HouseDetailDTO dto : houseDetails){
            list.add(dto.toEntity(house));
        }
        house.builder().houseDetails(list).build();
        return house;
    }
}
