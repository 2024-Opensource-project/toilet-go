package com.myspring.springmaster.dataAccess.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Entity
@Table(name = "Toilets")
public class Toilet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "toilet_name", length = 100) //화장실명
    private String toilet_name;

    @Column(name = "address", length = 255)  //도로명
    private String address;

    @Column(name = "male_toilets") //남성용 대변기
    private  Integer male_toilets;

    @Column(name = "male_urinals") //남성용 소변기
    private  Integer male_urinals;

    @Column(name = "male_disabled_toilets") //남성용 장애인 대변기
    private  Integer male_disabled_toilets;

    @Column(name = "male_child_toilets") //남성용 어린이 대변기
    private  Integer male_child_toilets;

    @Column(name = "female_toilets") //여성용 대변기
    private  Integer female_toilets;

    @Column(name = "female_disabled_toilets") //여성용 장애인 대변기
    private  Integer female_disabled_toilets;

    @Column(name = "female_child_toilets") //여성용 어린이 대변기
    private  Integer female_child_toilets;

    @Column(name = "latitude") //위도
    public BigDecimal latitude;

    @Column(name = "longitude") //경도
    private BigDecimal longitude;

    @Column(name = "opening_hours", length = 50)  //개방시간
    private String opening_hours;

    @Column(name = "emergency_bell_installed")  //비상벨 설치 여부
    private Boolean emergency_bell_installed;

    @Column(name = "emergency_bell_location", length = 255)  //비상벨 설치 장소
    private String emergency_bell_location;

    @Column(name = "entrance_cctv_installed")  //화장실 입구 CCTV 설치 여부
    private Boolean entrance_cctv_installed;

    @Column(name = "diaper_changing_station")  //기저귀 교환대 유무
    private Boolean diaper_changing_station;

    @Column(name = "diaper_changing_station_location", length = 255)  //기저귀 교환대 장소
    private String diaper_changing_station_location;

    @Builder
    public Toilet(Long id, String toilet_name, String address, BigDecimal latitude, BigDecimal longitude
            , int male_toilets, int male_urinals, int male_disabled_toilets, int male_child_toilets,
                  int female_toilets, int female_disabled_toilets, int female_child_toilets,
                  String opening_hours, Boolean emergency_bell_installed, String emergency_bell_location,
                  Boolean entrance_cctv_installed, Boolean diaper_changing_station, String diaper_changing_station_location){
        this.id = id;
        this.toilet_name = toilet_name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.male_toilets = male_toilets;
        this.male_urinals = male_urinals;
        this.male_disabled_toilets = male_disabled_toilets;
        this.male_child_toilets = male_child_toilets;
        this.female_toilets = female_toilets;
        this.female_disabled_toilets = female_disabled_toilets;
        this.female_child_toilets = female_child_toilets;
        this.opening_hours = opening_hours;
        this.emergency_bell_installed = emergency_bell_installed;
        this.emergency_bell_location = emergency_bell_location;
        this.entrance_cctv_installed = entrance_cctv_installed;
        this.diaper_changing_station = diaper_changing_station;
        this.diaper_changing_station_location = diaper_changing_station_location;

    }
}
