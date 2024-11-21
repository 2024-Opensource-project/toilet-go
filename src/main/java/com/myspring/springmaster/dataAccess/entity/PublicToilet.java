package com.myspring.springmaster.dataAccess.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PublicToilets")
@Data
public class PublicToilet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "toilet_name", nullable = false)
    private String toiletName; // 화장실명

    @Column(name = "address", nullable = false)
    private String address; // 소재지 도로명 주소

    @Column(name = "male_toilets", columnDefinition = "INT DEFAULT 0")
    private Integer maleToilets; // 남성용 대변기 수

    @Column(name = "male_urinals", columnDefinition = "INT DEFAULT 0")
    private Integer maleUrinals; // 남성용 소변기 수

    @Column(name = "male_disabled_toilets", columnDefinition = "INT DEFAULT 0")
    private Integer maleDisabledToilets; // 남성용 장애인용 대변기 수

    @Column(name = "male_child_toilets", columnDefinition = "INT DEFAULT 0")
    private Integer maleChildToilets; // 남성용 어린이용 대변기 수

    @Column(name = "female_toilets", columnDefinition = "INT DEFAULT 0")
    private Integer femaleToilets; // 여성용 대변기 수

    @Column(name = "female_disabled_toilets", columnDefinition = "INT DEFAULT 0")
    private Integer femaleDisabledToilets; // 여성용 장애인용 대변기 수

    @Column(name = "female_child_toilets", columnDefinition = "INT DEFAULT 0")
    private Integer femaleChildToilets; // 여성용 어린이용 대변기 수

    @Column(name = "opening_hours", columnDefinition = "VARCHAR(50) DEFAULT '24시간'")
    private String openingHours; // 개방시간

    @Column(name = "emergency_bell_installed", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean emergencyBellInstalled; // 비상벨 설치 여부

    @Column(name = "emergency_bell_location")
    private String emergencyBellLocation; // 비상벨 설치 장소

    @Column(name = "entrance_cctv_installed", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean entranceCctvInstalled; // 화장실 입구 CCTV 설치 여부

    @Column(name = "diaper_changing_station", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean diaperChangingStation; // 기저귀 교환대 유무

    @Column(name = "diaper_changing_station_location")
    private String diaperChangingStationLocation; // 기저귀 교환대 장소

    @Column(name = "longitude")
    private Double longitude; // 경도

    @Column(name = "latitude")
    private Double latitude; // 위도
}
