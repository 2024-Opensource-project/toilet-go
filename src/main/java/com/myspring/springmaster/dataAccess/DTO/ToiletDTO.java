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
    private Integer id;
    private String toilet_name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String male_toilets;
    private String male_urinals;
    private String male_disabled_toilets;
    private String male_child_toilets;
    private String female_toilets;
    private String female_disabled_toilets;
    private String female_child_toilets;
    private String opening_hours;
    private Boolean emergency_bell_installed;
    private String emergency_bell_location;
    private Boolean entrance_cctv_installed;
    private Boolean diaper_changing_station;
    private String diaper_changing_station_location;

}
