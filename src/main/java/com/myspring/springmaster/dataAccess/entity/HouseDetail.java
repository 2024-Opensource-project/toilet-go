package com.myspring.springmaster.dataAccess.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myspring.springmaster.dataAccess.DTO.HouseDetailDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "house_details")
@ToString
public class HouseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "house_id")
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

    public List<HouseDetail> toEntity(List<HouseDetailDTO> dtos) {
        List<HouseDetail> houseDetails = new ArrayList<>();
        for(HouseDetailDTO dto : dtos) {
            HouseDetail houseDetail = new HouseDetail();
            houseDetail.id = dto.getId();
            houseDetail.house = dto.getHouse();
            houseDetail.type = dto.getType();
            houseDetail.size = dto.getSize();
            houseDetail.supplyCount = dto.getSupplyCount();
            houseDetail.deposit = dto.getDeposit();
            houseDetail.monthlyRent = dto.getMonthlyRent();
            houseDetail.imageUrl = dto.getImageUrl();
            houseDetails.add(houseDetail);
        }
        return houseDetails;
    }
}
