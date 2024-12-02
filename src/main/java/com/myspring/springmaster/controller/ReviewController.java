package com.myspring.springmaster.controller;

import com.myspring.springmaster.dataAccess.DTO.ReviewDTO;
import com.myspring.springmaster.dataAccess.DTO.ToiletDTO;
import com.myspring.springmaster.service.ReviewService;
import com.myspring.springmaster.service.ToiletService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews/toilet")
public class ReviewController {

    private final ReviewService reviewService;
    private final ToiletService toiletService;

    @Autowired
    public ReviewController(ReviewService reviewService, ToiletService toiletService) {
        this.reviewService = reviewService;
        this.toiletService = toiletService;
    }

    @GetMapping("/{toiletId}")
    public String getReviewsByToiletId(@PathVariable Long toiletId, Model model) {
        List<ReviewDTO> reviews = reviewService.getReviewsByToiletId(toiletId);

        // 평균 평점과 리뷰 갯수 계산
        double averageRating = reviews.stream()
                .mapToInt(ReviewDTO::getRating)
                .average()
                .orElse(0.0);

        int reviewCount = reviews.size();

        // 화장실 이름 가져오기 (예: toiletService 사용)
        String toiletName = toiletService.getToilet(toiletId.intValue()).getToilet_name();

        model.addAttribute("reviews", reviews);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("reviewCount", reviewCount);
        model.addAttribute("toiletName", toiletName);
        model.addAttribute("toiletId", toiletId);

        return "reviews/allReviews";
    }

    @GetMapping("/{toiletId}/new")
    public String showReviewForm(@PathVariable Long toiletId, Model model) {
        ToiletDTO toilet = toiletService.getToilet(toiletId.intValue()); // ToiletService에서 화장실 정보 가져오기
        model.addAttribute("toiletName", toilet.getToilet_name()); // 화장실 이름 전달
        model.addAttribute("toiletId", toiletId); // 화장실 ID 전달
        return "reviews/reviewForm"; // 리뷰 작성 페이지 반환
    }


    @PostMapping("/{toiletId}/add")
    public String addReview(@PathVariable Long toiletId, @ModelAttribute ReviewDTO reviewDTO, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            userId = "t";
//            model.addAttribute("error", "로그인 후 리뷰를 작성할 수 있습니다.");
//            return "redirect:/signin"; // 로그인 페이지로 리다이렉트
        }

        // userId와 toiletId를 설정
        reviewDTO.setUserId(userId);
        reviewDTO.setToiletId(toiletId); // ReviewDTO에 toiletId가 필요하다면 설정

        try {
            // 리뷰 추가 서비스 호출
            reviewService.addReview(reviewDTO);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "리뷰를 추가하는 동안 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/reviews/toilet/" + toiletId;
        }

        // 성공 메시지 설정 및 화장실 리뷰 페이지로 리다이렉트
        model.addAttribute("success", "리뷰가 성공적으로 추가되었습니다!");
        return "redirect:/reviews/toilet/" + toiletId;
    }

    // 정렬 API 추가
    @GetMapping("/sort")
    @ResponseBody
    public List<ReviewDTO> sortReviews(@RequestParam String option, @RequestParam Long toiletId) {
        return reviewService.getSortedReviews(toiletId, option);
    }

}
