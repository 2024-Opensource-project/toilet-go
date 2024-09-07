package com.myspring.springmaster.dataAccess.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Builder
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Role() {}
}
