package com.myspring.springmaster.controller;

import com.myspring.springmaster.dataAccess.DAO.HouseDAO;
import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.DTO.HouseDetailDTO;
import com.myspring.springmaster.dataAccess.entity.House;
import com.myspring.springmaster.service.HouseService;
import com.myspring.springmaster.service.MapApiService;
import com.myspring.springmaster.service.NaverMapApi;
import com.myspring.springmaster.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Controller
public class HouseController {

    private final HouseService houseService;
    private final UserService userService;

    @Autowired
    public HouseController(HouseService houseService, UserService userService) {
        this.houseService = houseService;
        this.userService = userService;
    }

    @GetMapping("/house/detail/{id}")
    public String showHouse(Model model, @PathVariable int id) {
        HouseDTO houseDTO = houseService.getHouse(id);
        if(houseDTO != null) {
            model.addAttribute("house", houseDTO);
        }
        else{
            return "error";
        }
        return "house/detailView";
    }

    @GetMapping("house/list")
    public String showHouseList(Model model) {
        model.addAttribute("houses", houseService.getAllActiveHousesList());
        return "house/listView";
    }

    @PostMapping("house/list")
    public String showHouseList(Model model, @ModelAttribute HouseDTO houseDTO) {
        List<HouseDTO> houseList = houseService.getHousesByFilter(houseDTO);
        model.addAttribute("houses", houseList);
        return "house/listView";
    }

    @GetMapping("house/near")
    public String showHouseNear(Model model) {
        model.addAttribute("houses", houseService.getAllActiveHousesList());
        return "house/nearView";
    }

    @PostMapping("house/near")
    public String showHouseNear(Model model, String address, int distance) {
        model.addAttribute("houses", houseService.getNearHouseList(address, distance));
        return "house/nearView";
    }

    @GetMapping("house/mapview")
    public String showHouseMapView() {
        return "house/mapView";
    }

    @PostMapping("house/mapview")
    @ResponseBody
    public List<double[]> getAllHousesLocation() {
        return houseService.getAllHousesLocation();
    }

    @PostMapping("house/latandlng")
    @ResponseBody
    public double[] getLatAndLng(@RequestParam String address){
        if(address==null || address.isEmpty() || address.equals("null")){
            return new double[0];
        }
        return houseService.getLatitudeAndLongitudeAsDouble(address);
    }

    @GetMapping("house/add")
    public String addHouse(HttpSession session, RedirectAttributes redirectAttributes){
        if(userService.isAdmin(session)){
            return "house/addHouse";
        }
        redirectAttributes.addFlashAttribute("message", "You are not admin");
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("house/add")
    public String addHouse(@RequestBody HouseDTO house, HttpSession session) {
        if(userService.isAdmin(session)){
            houseService.addHouse(house);
            return "success";
        }
        return "Allowed only admin!";
    }
}
