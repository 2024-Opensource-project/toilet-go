package com.myspring.springmaster.controller;

import com.myspring.springmaster.dataAccess.DAO.HouseDAO;
import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.service.HouseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
public class HouseController {
    @GetMapping("/house/{idx}")
    public String showHouse(Model model, @PathVariable int idx) throws SQLException, ClassNotFoundException {
        HouseService houseService = new HouseService();
        model.addAttribute("house", houseService.getHouse(idx));
        return "house/detailView";
    }

    @ResponseBody
    @PostMapping("house/add")
    public String addHouse(@RequestBody HouseDTO house) throws ClassNotFoundException, SQLException {
        System.out.println(house.toString());
        HouseService houseService = new HouseService();
        houseService.addHouse(house);
        return "success";
    }
}
