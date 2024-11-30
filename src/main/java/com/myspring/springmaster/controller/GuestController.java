package com.myspring.springmaster.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {

    @GetMapping("/guest-login")
    public String guestLogin(HttpSession session) {
        // 비회원 사용자 정보를 세션에 저장
        session.setAttribute("userRole", "GUEST");
        return "redirect:/home";  // 비회원 사용자가 접근할 수 있는 페이지로 리다이렉트
    }
}
