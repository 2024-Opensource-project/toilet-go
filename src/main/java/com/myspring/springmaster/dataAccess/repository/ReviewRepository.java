package com.myspring.springmaster.dataAccess.repository;

import com.myspring.springmaster.dataAccess.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByToiletId(Long toiletId);

    // 특정 화장실의 평균 평점 계산
    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.toilet.id = :toiletId")
    double calculateAverageRatingByToiletId(Long toiletId);

    // 특정 화장실의 리뷰 갯수 계산
    @Query("SELECT COUNT(r) FROM Review r WHERE r.toilet.id = :toiletId")
    long countReviewsByToiletId(Long toiletId);
}