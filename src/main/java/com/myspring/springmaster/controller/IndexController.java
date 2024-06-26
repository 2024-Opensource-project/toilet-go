package com.myspring.springmaster.controller;

import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.service.HouseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class IndexController {
    private final HouseService houseService;

    @Autowired
    public IndexController(HouseService houseService) {
        this.houseService = houseService;
    }
/*
    @GetMapping("/")
    public String indexController(Model model, HttpSession session) throws SQLException, ClassNotFoundException {
        ArrayList<HouseDTO> houseList = houseService.getHouseList();
        model.addAttribute("houseList", houseList);
        return "index";
    }

 */
}
