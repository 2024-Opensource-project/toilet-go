package com.myspring.springmaster.dataAccess.entity;

import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
    public List<HouseDetail> houseDetails;

    @Builder
    public House(HouseDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.address = dto.getAddress();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.status = dto.getStatus();
        this.moveInDate = dto.getMoveInDate();
        this.applyStartDate = dto.getApplyStartDate();
        this.applyEndDate = dto.getApplyEndDate();
        this.houseDetails = new HouseDetail().toEntity(dto.getHouseDetails());
    }

    public House() {

    }


    // Getters and setters
}
