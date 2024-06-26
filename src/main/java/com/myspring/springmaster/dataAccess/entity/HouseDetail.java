package com.myspring.springmaster.dataAccess.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "house_details")
public class HouseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", nullable = false)
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

    // Getters and setters
}
