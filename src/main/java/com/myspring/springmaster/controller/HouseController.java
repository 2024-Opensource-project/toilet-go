package com.myspring.springmaster.controller;

import com.myspring.springmaster.dataAccess.DAO.HouseDAO;
import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
public class HouseController {
    @GetMapping("/house/{idx}")
    public String showHouse(Model model, @PathVariable int idx) throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        model.addAttribute("house", houseDAO.getHouseInfo(idx));
        return "house/detailView";
    }

    @ResponseBody
    @PostMapping("house/add")
    public String addHouse(@ModelAttribute HouseDTO house) throws ClassNotFoundException, SQLException {
        HouseDAO houseDAO = new HouseDAO();
        houseDAO.addHouse(house);
        return "success";
    }
}
