package com.myspring.springmaster.dataAccess.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "toilet_id", referencedColumnName = "id", nullable = false)
    private Toilet toilet;

    public Favorite() {
    }

    public Favorite(User user, Toilet toilet) {
        this.user = user;
        this.toilet = toilet;
    }
    @Override
    public String toString() {
        return "FavoriteDTO{" +
                "userId=" + user.getId() +
                ", toiletId=" + toilet.getId() +
                '}';
    }
}
