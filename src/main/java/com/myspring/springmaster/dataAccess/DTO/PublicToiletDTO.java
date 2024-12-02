package com.myspring.springmaster.dataAccess.DTO;

import lombok.Data;

@Data
public class PublicToiletDTO {
    private Integer id;
    private String toiletName; // 화장실명
    private String address; // 소재지 도로명 주소
    private Integer maleToilets; // 남성용 대변기 수
    private Integer maleUrinals; // 남성용 소변기 수
    private Integer maleDisabledToilets; // 남성용 장애인용 대변기 수
    private Integer maleChildToilets; // 남성용 어린이용 대변기 수
    private Integer femaleToilets; // 여성용 대변기 수
    private Integer femaleDisabledToilets; // 여성용 장애인용 대변기 수
    private Integer femaleChildToilets; // 여성용 어린이용 대변기 수
    private String openingHours; // 개방시간
    private Boolean emergencyBellInstalled; // 비상벨 설치 여부
    private String emergencyBellLocation; // 비상벨 설치 장소
    private Boolean entranceCctvInstalled; // 화장실 입구 CCTV 설치 여부
    private Boolean diaperChangingStation; // 기저귀 교환대 유무
    private String diaperChangingStationLocation; // 기저귀 교환대 장소
    private Double longitude; // 경도
    private Double latitude; // 위도
}
