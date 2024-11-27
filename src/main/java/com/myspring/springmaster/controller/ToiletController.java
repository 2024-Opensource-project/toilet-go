package com.myspring.springmaster.controller;

import com.myspring.springmaster.dataAccess.DTO.ToiletDTO;
import com.myspring.springmaster.service.ToiletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ToiletController {
    private final ToiletService toiletService;

    @Autowired
    public ToiletController(ToiletService toiletService) {
        this.toiletService = toiletService;
    }

//    //화장실 리스트 조회
//    @GetMapping("toilet/list")
//    public String showToiletList(Model model){
//        model.addAttribute("toilets",toiletService.getToilets(100));
//        return "toilet/listView";
//    }

    @GetMapping("toilet/list")
    public String showToiletList(
            @RequestParam(defaultValue = "1") int page, // 기본값 1페이지
            @RequestParam(defaultValue = "100") int size, // 한 페이지당 데이터 수
            Model model) {
        int zeroBasedPage = page - 1; // 1-based 페이지를 0-based로 변환
        Pageable pageable = PageRequest.of(zeroBasedPage, size);

        Page<ToiletDTO> toilets = toiletService.getToiletsPage(pageable);

        int totalPages = toilets.getTotalPages();
        int currentGroup = (page - 1) / 10; // 현재 페이지 그룹 (10개 단위)
        int startPage = currentGroup * 10 + 1; // 해당 그룹의 시작 페이지 번호 (1부터 시작)
        int endPage = Math.min(startPage + 9, totalPages); // 해당 그룹의 마지막 페이지 번호

        model.addAttribute("toilets", toilets.getContent());
        model.addAttribute("currentPage", page); // 1-based 페이지
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("size", size);
        return "toilet/listView";
    }



    @PostMapping("/toilet/list")
    public String showToiletList(Model model, @ModelAttribute ToiletDTO toiletDTO) {
        List<ToiletDTO> toilets = toiletService.getToiletsByFilter(toiletDTO, 100);
        model.addAttribute("toilets", toilets);

        model.addAttribute("filter", toiletDTO);
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
    public String showToiletMapView(){return "toilet/mapView";}
    @PostMapping("toilet/mapView")
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
