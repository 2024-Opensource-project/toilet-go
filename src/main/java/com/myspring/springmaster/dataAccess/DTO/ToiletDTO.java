package com.myspring.springmaster.dataAccess.DTO;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToiletDTO {
    private Long id; // 수정: Integer → Long
    private String toilet_name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer male_toilets; // 수정: String → Integer
    private Integer male_urinals; // 수정: String → Integer
    private Integer male_disabled_toilets; // 수정: String → Integer
    private Integer male_child_toilets; // 수정: String → Integer
    private Integer female_toilets; // 수정: String → Integer
    private Integer female_disabled_toilets; // 수정: String → Integer
    private Integer female_child_toilets; // 수정: String → Integer
    private String opening_hours;
    private Boolean emergency_bell_installed;
    private String emergency_bell_location;
    private Boolean entrance_cctv_installed;
    private Boolean diaper_changing_station;
    private String diaper_changing_station_location;
}
