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
    private Integer id;

    @Column(name = "type", length = 10)
    private String type;

    @Column(name = "size", length = 20)
    private String size;

    @Column(name = "supply_count")
    private Integer supplyCount;

    @Column(name = "deposit")
    private Integer deposit;

    @Column(name = "monthly_rent")
    private Integer monthlyRent;

    @Column(name = "image_url", length = 255)
    private String imageUrl;


    public HouseDetail(int id, String type, String size, int supplyCount, int deposit, int monthlyRent, String imageUrl) {
        this.id = id;
        this.type = type;
        this.size = size;
        this.supplyCount = supplyCount;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.imageUrl = imageUrl;
    }

    public HouseDetail() {

    }
}
