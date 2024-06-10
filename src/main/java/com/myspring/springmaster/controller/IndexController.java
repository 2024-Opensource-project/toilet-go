package com.myspring.springmaster.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    @GetMapping("/")
    public String indexController(Model model, HttpSession session){
        model.addAttribute("userId", session.getAttribute("user"));
        return "index";
    }
}
