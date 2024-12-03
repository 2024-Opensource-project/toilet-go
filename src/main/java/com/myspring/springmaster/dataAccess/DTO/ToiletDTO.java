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
    private String cityOrDistrict;
    private double averageRating; // 평균 평점
    private int reviewCount;      // 리뷰 개수

    // Getter와 Setter
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityOrDistrict() {
        return cityOrDistrict;
    }

    public void setCityOrDistrict(String cityOrDistrict) {
        this.cityOrDistrict = cityOrDistrict;
    }

    public Boolean getEmergency_bell_installed() {
        return emergency_bell_installed;
    }

    public Boolean getEntrance_cctv_installed() {
        return entrance_cctv_installed;
    }

    public Boolean getDiaper_changing_station() {
        return diaper_changing_station;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }
}
