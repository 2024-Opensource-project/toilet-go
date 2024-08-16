package com.myspring.springmaster.controller;

import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.DTO.PreviewHouseDTO;
import com.myspring.springmaster.service.HouseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {
    private final HouseService houseService;

    @Autowired
    public IndexController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/")
    public String indexController(Model model, HttpSession session) {
        final int HOUSE_COUNT = 10;
        List<PreviewHouseDTO> houseList = houseService.getPreviewHouses(HOUSE_COUNT);
        model.addAttribute("houseList", houseList);
        model.addAttribute("name", session.getAttribute("name"));
        return "index";
    }


}
