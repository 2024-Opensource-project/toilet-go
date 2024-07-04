package com.myspring.springmaster.dataAccess.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myspring.springmaster.dataAccess.DTO.HouseDetailDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "house_details")
@SuperBuilder
public class HouseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "house_id")
    @JsonBackReference
    private House house;

    @Column(name = "type", length = 10)
    private String type;

    @Column(name = "size", length = 20)
    private String size;

    @Column(name = "supply_count")
    private int supplyCount;

    @Column(name = "deposit")
    private int deposit;

    @Column(name = "monthly_rent")
    private int monthlyRent;

    @Column(name = "image_url", length = 255)
    private String imageUrl;


    public void setHouse(House house) {
        this.house = house;
    }

    public HouseDetail(int id, House house, String type, String size, int supplyCount, int deposit, int monthlyRent, String imageUrl) {
        this.id = id;
        this.house = house;
        this.type = type;
        this.size = size;
        this.supplyCount = supplyCount;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.imageUrl = imageUrl;
    }

    public HouseDetail() {

    }

/*
    public List<HouseDetail> toEntity(List<HouseDetailDTO> dtos, House it) {
        List<HouseDetail> houseDetails = new ArrayList<>();
        for(HouseDetailDTO dto : dtos) {
            HouseDetail houseDetail = new HouseDetail();
            houseDetail.id = dto.getId();
            //houseDetail.house = it;
            houseDetail.type = dto.getType();
            houseDetail.size = dto.getSize();
            houseDetail.supplyCount = dto.getSupplyCount();
            houseDetail.deposit = dto.getDeposit();
            houseDetail.monthlyRent = dto.getMonthlyRent();
            houseDetail.imageUrl = dto.getImageUrl();
            houseDetails.add(houseDetail);
        }
        return houseDetails;
    }*/
}
