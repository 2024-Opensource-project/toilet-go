package com.myspring.springmaster.controller;

import com.myspring.springmaster.dataAccess.DAO.HouseDAO;
import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.service.HouseService;
import com.myspring.springmaster.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@Controller
public class HouseController {
    @GetMapping("/house/detailView/{idx}")
    public String showHouse(Model model, @PathVariable int idx) throws SQLException, ClassNotFoundException {
        HouseService houseService = new HouseService();
        model.addAttribute("house", houseService.getHouse(idx));
        return "house/detailView";
    }

    @GetMapping("house/list")
    public String showHouseList(Model model) throws SQLException, ClassNotFoundException {
        HouseService houseService = new HouseService();
        model.addAttribute("houses", houseService.getAllActiveHousesList());
        return "house/listView";
    }

    @PostMapping("house/list")
    public String showHouseList(Model model, @RequestParam HouseDTO houseDTO) throws SQLException, ClassNotFoundException {
        HouseService houseService = new HouseService();
        model.addAttribute("houses", houseService.getAllActiveHousesList());
        return "house/listView";
    }

    @GetMapping("house/near")
    public String showHouseNear(Model model, @RequestParam String address) throws SQLException, ClassNotFoundException {
        HouseService houseService = new HouseService();
        model.addAttribute("houses", houseService.getNearHouseList(address));
        return "house/listView";
    }

    @GetMapping("house/add")
    public String addHouse(Model model, HttpSession session, RedirectAttributes redirectAttributes){
        UserService userService = new UserService();
        if(userService.isAdmin(session)){
            return "house/addHouse";
        }
        redirectAttributes.addFlashAttribute("message", "You are not admin");
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("house/add")
    public String addHouse(@RequestBody HouseDTO house, HttpSession session) throws ClassNotFoundException, SQLException {
        UserService userService = new UserService();
        if(userService.isAdmin(session)){
            HouseService houseService = new HouseService();
            houseService.addHouse(house);
            return "success";
        }
        return "Allowed only admin!";
    }
}
