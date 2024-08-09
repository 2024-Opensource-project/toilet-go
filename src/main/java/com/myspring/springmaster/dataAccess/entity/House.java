package com.myspring.springmaster.dataAccess.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.DTO.HouseDetailDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Entity
@Table(name = "houses")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "latitude")
    public BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "move_in_date")
    @Temporal(TemporalType.TIMESTAMP)
    private String  moveInDate;

    @Column(name = "apply_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private String applyStartDate;

    @Column(name = "apply_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private String applyEndDate;

    @Column(name = "company")
    private String company;

    @Column(name = "redirect_url_form")
    private String redirectUrlForm;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "house_id", updatable = false, nullable = false)
    private List<HouseDetail> houseDetails;

    @Builder
    public House(Long id, String name, String address, BigDecimal latitude, BigDecimal longitude,
                 String status, String moveInDate, String applyStartDate, String applyEndDate,
                 String company, String redirectUrlForm, List<HouseDetail> houseDetails){
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.moveInDate = moveInDate;
        this.applyStartDate = applyStartDate;
        this.applyEndDate = applyEndDate;
        this.company = company;
        this.redirectUrlForm = redirectUrlForm;
        this.houseDetails = houseDetails;
    }


    public House() {

    }
}
