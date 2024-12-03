package com.myspring.springmaster.controller;

import com.myspring.springmaster.dataAccess.DTO.ReviewDTO;
import com.myspring.springmaster.dataAccess.DTO.ToiletDTO;
import com.myspring.springmaster.service.ReviewService;
import com.myspring.springmaster.service.ToiletService;
import com.myspring.springmaster.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reviews/toilet")
public class ReviewController {

    private final ReviewService reviewService;
    private final ToiletService toiletService;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService, ToiletService toiletService, UserService userService) {
        this.reviewService = reviewService;
        this.toiletService = toiletService;
        this.userService = userService;
    }

    @GetMapping("/{toiletId}")
    public String getReviewsByToiletId(@PathVariable Long toiletId, Model model) {
        List<ReviewDTO> reviews = reviewService.getReviewsByToiletId(toiletId);

        // 평균 평점과 리뷰 갯수 계산
        double averageRating = reviews.stream()
                .mapToInt(ReviewDTO::getRating)
                .average()
                .orElse(0.0);

        // 소수점 둘째 자리 반올림
        averageRating = Math.round(averageRating * 100.0) / 100.0;

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
    public String showReviewForm(@PathVariable Long toiletId,
                                 @RequestParam(value = "referer", required = false) String referer,
                                 Model model, HttpSession session) {
        // 인증 확인
        if (!userService.isLoggedIn(session)) {
            model.addAttribute("error", "리뷰를 작성하려면 로그인이 필요합니다.");
            if (referer != null) {
                // 원래 페이지로 돌아가기
                return "redirect:" + referer;
            }
            return "redirect:/"; // referer가 없으면 홈으로 이동
        }

        ToiletDTO toilet = toiletService.getToilet(toiletId.intValue()); // 화장실 정보 가져오기
        model.addAttribute("toiletName", toilet.getToilet_name());
        model.addAttribute("toiletId", toiletId);
        return "reviews/reviewForm"; // 리뷰 작성 페이지 반환
    }


    @PostMapping("/{toiletId}/add")
    public ModelAndView addReview(@PathVariable Long toiletId,
                                  @ModelAttribute ReviewDTO reviewDTO,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        String userId = null;
        Object sessionId = session.getAttribute("id");
        if (sessionId instanceof Long) {
            userId = String.valueOf(sessionId);
        } else if (sessionId instanceof Integer) {
            userId = String.valueOf(sessionId);
        }
        reviewDTO.setUserId(userId);
        reviewDTO.setToiletId(toiletId);

        if (userService.isLoggedIn(session)) {
            try {
                reviewService.addReview(reviewDTO);
                redirectAttributes.addFlashAttribute("message", "리뷰가 성공적으로 추가되었습니다.");
                return new ModelAndView("redirect:/reviews/toilet/" + toiletId); // 원하는 경로로 리디렉션
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "리뷰를 추가하는 중 오류가 발생했습니다: " + e.getMessage());
                return new ModelAndView("redirect:/reviews/toilet/" + toiletId);
            }
        }
        redirectAttributes.addFlashAttribute("error", "로그인이 필요한 서비스입니다.");
        return new ModelAndView("redirect:/login");
    }



    // 정렬 API 추가
    @GetMapping("/sort")
    @ResponseBody
    public List<ReviewDTO> sortReviews(@RequestParam String option, @RequestParam Long toiletId) {
        return reviewService.getSortedReviews(toiletId, option);
    }

    //로그인 여부 확인 엔드포인트
    @GetMapping("/check-login")
    public ResponseEntity<Map<String, Object>> checkLogin(HttpSession session) {
        boolean isLoggedIn = userService.isLoggedIn(session);
        Map<String, Object> response = new HashMap<>();
        response.put("loggedIn", isLoggedIn);

        if (isLoggedIn) {
            response.put("message", "로그인 상태입니다.");
        } else {
            response.put("message", "로그인이 필요합니다.");
        }

        return ResponseEntity.ok(response);
    }

}
