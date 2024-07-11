package com.myspring.springmaster.controller;

import com.myspring.springmaster.dataAccess.DTO.UserDTO;
import com.myspring.springmaster.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
}
