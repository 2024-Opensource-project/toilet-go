package com.myspring.springmaster.dataAccess.repository;

import com.myspring.springmaster.dataAccess.entity.Favorite;
import com.myspring.springmaster.dataAccess.entity.User;
import com.myspring.springmaster.dataAccess.entity.Toilet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    // 특정 사용자와 화장실에 대한 즐겨찾기 존재 여부 확인
    Optional<Favorite> findByUserAndToilet(User user, Toilet toilet);

    // 사용자에 의한 즐겨찾기 목록 조회
    List<Favorite> findByUser(User user);

}
