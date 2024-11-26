package com.myspring.springmaster.controller;

import com.myspring.springmaster.service.ToiletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ToiletController {
    private final ToiletService toiletService;

    @Autowired
    public ToiletController(ToiletService toiletService) {
        this.toiletService = toiletService;
    }

    //화장실 리스트 조회
    @GetMapping("toilet/list")
    public String showToiletList(Model model){
        model.addAttribute("toilets",toiletService.getToilets(100));
        return "toilet/listView";
    }

    //근처 화장실 조회
    @GetMapping("toilet/near")
    public String showHouseNear(Model model){
        model.addAttribute("toilets", toiletService.getAllToiletsLocation());
        return "toilet/nearView";
    }
    @PostMapping("toilet/near")
    public String showHouseNear(Model model, String address, int distance){
        model.addAttribute("toilets", toiletService.getNearToiletList(address,distance));
        return "toilet/nearView";
    }

    //mapview
    @GetMapping("toilet/mapView")
    public List<double[]> getAllToiletsLocation(){return toiletService.getAllToiletsLocation();}

    @PostMapping("toilet/latandlng")
    @ResponseBody
    public double[] getLatAndLng(@RequestParam String address){
        if (address == null || address.isEmpty() || address.equals("null")){
            return new double[0];
        }
        return toiletService.getLatitudeAndLongitudeAsDouble(address);
    }
}
