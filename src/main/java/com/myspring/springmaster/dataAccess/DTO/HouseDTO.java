package com.myspring.springmaster.dataAccess.DTO;

import com.myspring.springmaster.dataAccess.entity.House;
import com.myspring.springmaster.dataAccess.entity.HouseDetail;
import lombok.*;

import java.math.BigDecimal;
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
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String status;
    private String moveInDate;
    private String applyStartDate;
    private String applyEndDate;
    private List<HouseDetailDTO> houseDetails;
}
