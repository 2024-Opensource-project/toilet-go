/*package com.myspring.springmaster.dataAccess.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_info")
public class userEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int _idx;

    @Column(nullable = false, unique = true, length = 16)
    private String id;

    @Column(nullable = false, length = 256)
    private String pw;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(nullable = false, length = 64)
    private String email;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int level = 0;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private long money = 0;
}
*/