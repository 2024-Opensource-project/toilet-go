package com.myspring.springmaster.dataAccess.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.DTO.HouseDetailDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Entity
@Table(name = "houses")
@ToString
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "move_in_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date moveInDate;

    @Column(name = "apply_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date applyStartDate;

    @Column(name = "apply_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date applyEndDate;


    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    public List<HouseDetail> houseDetails;

    @Builder
    public House(int id, String name, String address, double latitude, double longitude, String status, Date moveInDate, Date applyStartDate, Date applyEndDate, List<HouseDetail> houseDetails){
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.moveInDate = moveInDate;
        this.applyStartDate = applyStartDate;
        this.applyEndDate = applyEndDate;
        this.houseDetails = houseDetails;
    }

    public House() {

    }

    public House toEntity(HouseDTO houseDTO) {
        List<HouseDetail> list = new ArrayList<>();
        for(HouseDetailDTO dto : houseDTO.getHouseDetails()){
            list.add(dto.toEntity(this));
        }
        this.id = houseDTO.getId();
        this.name = houseDTO.getName();
        this.address = houseDTO.getAddress();
        this.latitude = houseDTO.getLatitude();
        this.longitude = houseDTO.getLongitude();
        this.status = houseDTO.getStatus();
        this.moveInDate = houseDTO.getMoveInDate();
        this.applyStartDate = houseDTO.getApplyStartDate();
        this.applyEndDate = houseDTO.getApplyEndDate();
        this.houseDetails = list;
        return this;
    }

    public void setHouseDetailsParent(){
        for(HouseDetail houseDetail : houseDetails){
            houseDetail.setHouse(this);
        }
    }

    // Getters and setters
}
