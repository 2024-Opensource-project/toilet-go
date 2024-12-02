package com.myspring.springmaster.dataAccess.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true, length = 50)
    private String userId;

    @Column(name = "password", nullable = true, length = 256)
    private String password;

    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = true, length = 16)
    @ColumnDefault("'N/A'")
    private String phoneNumber;

    @Column(name = "role_id")
    @ColumnDefault("1")
    private Integer roleId;

    @Column(name = "registration_id", length = 255)
    private String registrationId;

    @Builder
    public User(Long id, String userId, String password, String name, String email, String phoneNumber, Integer roleId, String registrationId) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
        this.registrationId = registrationId;
    }

    public User() {
    }
}
