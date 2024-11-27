package com.myspring.springmaster.controller;

import com.myspring.springmaster.dataAccess.DTO.UserDTO;
import com.myspring.springmaster.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signin")
    public String signin() {
        return "user/signin";
    }
    @PostMapping("signin")
    public String signin(@ModelAttribute UserDTO user, HttpSession session, RedirectAttributes redirect) {
        if(userService.login(user, session)){
            return "redirect:/";
        }
        redirect.addFlashAttribute("message", "Invalid username or password");
        return "redirect:/signin";
    }


    @GetMapping("/signup")
    public String signUp() {
        return "user/signup";
    }
    @PostMapping("/signup")
    public String signUp(@ModelAttribute UserDTO user, RedirectAttributes redirect){
        redirect.addFlashAttribute("message", userService.signUp(user));
        return "redirect:/";
    }


    @GetMapping("/signout")
    public String signOut(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // 아이디 찾기 요청 처리
    @GetMapping("/find-id")
    public String findUserId() {
        return "user/find-id";
    }
    @PostMapping("/find-id")
    public String findUserId(@RequestParam String email, RedirectAttributes redirectAttributes) {
        String userId = userService.findUserIdByEmail(email);
        if (userId != null) {
            redirectAttributes.addFlashAttribute("message", "Your UserId: " + userId);
        } else {
            redirectAttributes.addFlashAttribute("message", "No account found with the provided information.");
        }
        return "redirect:/find-id";
    }

    // 비밀번호 찾기 요청 처리
    @GetMapping("/find-password")
    public String findPassword() {
        return "user/find-password";
    }
    @PostMapping("/find-password")
    public String findPassword(@RequestParam String userId, @RequestParam String email, RedirectAttributes redirectAttributes) {
        String password = userService.findPassword(userId, email);
        if (password != null) {
            redirectAttributes.addFlashAttribute("message", "Your Password: " + password);
        } else {
            redirectAttributes.addFlashAttribute("message", "No account found with the provided information.");
        }
        return "redirect:/find-password";
    }
}
