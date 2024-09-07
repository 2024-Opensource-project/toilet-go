package com.myspring.springmaster.dataAccess.DTO;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseDTO {

    private Integer id;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String status;
    private String moveInDate;
    private String applyStartDate;
    private String applyEndDate;
    private String company;
    private String redirectUrlForm;
    private List<HouseDetailDTO> houseDetails;
}
