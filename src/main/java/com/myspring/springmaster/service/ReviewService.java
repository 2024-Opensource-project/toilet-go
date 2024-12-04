package com.myspring.springmaster.service;

import com.myspring.springmaster.dataAccess.DTO.ReviewDTO;
import com.myspring.springmaster.dataAccess.entity.Review;
import com.myspring.springmaster.dataAccess.entity.Toilet;
import com.myspring.springmaster.dataAccess.entity.User;
import com.myspring.springmaster.dataAccess.repository.ReviewRepository;
import com.myspring.springmaster.dataAccess.repository.ToiletRepository;
import com.myspring.springmaster.dataAccess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ToiletRepository toiletRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ToiletRepository toiletRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.toiletRepository = toiletRepository;
        this.userRepository = userRepository;
    }

    public List<ReviewDTO> getReviewsByToiletId(Long toiletId) {
        return reviewRepository.findByToiletId(toiletId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void addReview(ReviewDTO reviewDTO) {
        try {
            // 1. Toilet 정보 조회
            Toilet toilet = toiletRepository.findById(reviewDTO.getToiletId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid toilet ID: " + reviewDTO.getToiletId()));

            // 2. User 정보 조회
            User user = userRepository.findById(Integer.valueOf(reviewDTO.getUserId()));
            if (user == null) {
                throw new IllegalArgumentException("Invalid user ID: " + reviewDTO.getUserId());
            }

            // 3. Review 객체 생성 및 저장
            Review review = new Review();
            review.setToilet(toilet);
            review.setUser(user);
            review.setRating(reviewDTO.getRating());
            review.setReviewText(reviewDTO.getReviewText());

            // 4. Review 데이터 저장
            reviewRepository.save(review);
        } catch (IllegalArgumentException e) {
            // 예외 발생 시, 잘못된 입력값이나 데이터에 대한 명확한 메시지 로깅
            System.out.println("Error adding review: " + e.getMessage());
            throw e; // 클라이언트에 에러 메시지를 전달할 수 있도록 예외 재발생
        } catch (Exception e) {
            // 기타 예기치 않은 오류 처리
            System.out.println("Unexpected error: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while adding review", e);
        }
    }

    // Review 엔티티에 User 필드가 추가된 상태에서 정상 작동
    private ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setToiletId(review.getToilet().getId());
        dto.setUserId(review.getUser().getUserId());
        dto.setRating(review.getRating());
        dto.setReviewText(review.getReviewText());
        return dto;
    }

    public List<ReviewDTO> getSortedReviews(Long toiletId, String option) {
        List<Review> reviews = reviewRepository.findByToiletId(toiletId);

        if ("ratingDesc".equals(option)) {
            reviews.sort((a, b) -> b.getRating() - a.getRating()); // 평점 높은순
        } else if ("ratingAsc".equals(option)) {
            reviews.sort(Comparator.comparingInt(Review::getRating)); // 평점 낮은순
        } else {
            reviews.sort(Comparator.comparing(Review::getCreatedDate).reversed()); // 최신순
        }

        return reviews.stream().map(this::toDTO).collect(Collectors.toList());
    }


}
