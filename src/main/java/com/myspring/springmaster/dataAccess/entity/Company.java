package com.myspring.springmaster.dataAccess.entity;

import jakarta.persistence.*;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "apply_url", unique = true, nullable = false)
    private String applyUrl;
}
