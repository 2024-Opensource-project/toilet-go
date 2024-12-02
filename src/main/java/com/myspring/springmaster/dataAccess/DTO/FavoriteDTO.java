package com.myspring.springmaster.dataAccess.DTO;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FavoriteDTO {
    private Integer userId;
    private Integer toiletId;

    // 기본 생성자, getters, setters
    public FavoriteDTO() {
    }

    public FavoriteDTO(Integer userId, Integer toiletId) {
        this.userId = userId;
        this.toiletId = toiletId;
    }

    @Override
    public String toString() {
        return "FavoriteDTO{" +
                "userId=" + userId +
                ", toiletId=" + toiletId +
                '}';
    }


}
