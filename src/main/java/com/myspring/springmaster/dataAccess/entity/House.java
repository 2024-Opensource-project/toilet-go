package com.myspring.springmaster.dataAccess.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;
@Getter
@Entity
@Table(name = "houses")
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


    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER)
    public List<HouseDetail> houseDetails;

    // Getters and setters
}
