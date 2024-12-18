package com.myspring.springmaster.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.myspring.springmaster.dataAccess.DTO.ToiletDTO;
import com.myspring.springmaster.service.ToiletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    public String getToiletList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "전체") String address,
            @RequestParam(required = false) String cityOrDistrict,
            @RequestParam(required = false) Boolean emergency_bell_installed,
            @RequestParam(required = false) Boolean entrance_cctv_installed,
            @RequestParam(required = false) Boolean diaper_changing_station,
            Model model) {

        ToiletDTO filter = new ToiletDTO();

        filter.setAddress(address);
        filter.setCityOrDistrict(cityOrDistrict);
        filter.setEmergency_bell_installed(emergency_bell_installed);
        filter.setEntrance_cctv_installed(entrance_cctv_installed);
        filter.setDiaper_changing_station(diaper_changing_station);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ToiletDTO> toilets = toiletService.getToiletsByFilter(filter, pageable);

        int totalPages = toilets.getTotalPages();
        totalPages = (totalPages == 0) ? 1 : totalPages;

        // 페이지 그룹 계산 (10개씩 그룹화)
        int pageGroupSize = 10; // 페이지 그룹 크기
        int currentGroup = (page - 1) / pageGroupSize;
        int startPage = currentGroup * pageGroupSize + 1;
        int endPage = Math.min(startPage + pageGroupSize - 1, totalPages);

        model.addAttribute("toilets", toilets.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("filter", filter);

        return "toilet/listView";
    }

    @PostMapping("/toilet/list")
    public String showToiletList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String address,
            @RequestParam(required = false) String cityOrDistrict,
            @RequestParam(required = false) Boolean emergency_bell_installed,
            @RequestParam(required = false) Boolean entrance_cctv_installed,
            @RequestParam(required = false) Boolean diaper_changing_station,
            @ModelAttribute ToiletDTO filter,
            Model model) {
        // 주소 필터: 서울특별시 + 노원구 형식으로 결합
        String fullAddress = (cityOrDistrict != null && !cityOrDistrict.isEmpty())
                ? address + " " + cityOrDistrict
                : address;

        filter.setAddress(fullAddress);
        filter.setEmergency_bell_installed(emergency_bell_installed);
        filter.setEntrance_cctv_installed(entrance_cctv_installed);
        filter.setDiaper_changing_station(diaper_changing_station);

        int zeroBasedPage = page - 1;
        Pageable pageable = PageRequest.of(zeroBasedPage, size);

        Page<ToiletDTO> toilets = toiletService.getToiletsByFilter(filter, pageable);

        int totalPages = toilets.getTotalPages();
        int currentGroup = (page - 1) / 10;
        int startPage = currentGroup * 10 + 1;
        int endPage = Math.min(startPage + 9, totalPages);

        model.addAttribute("toilets", toilets.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("filter", filter);

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
    @ResponseBody
    public List<ToiletDTO> getAllToiletsLocation(@RequestBody JsonNode requestBody) {
        JsonNode location = requestBody.path("location");
        JsonNode min = location.path("min");
        JsonNode max = location.path("max");

        double minLat = min.path("latitude").asDouble();
        double minLng = min.path("longitude").asDouble();
        double maxLat = max.path("latitude").asDouble();
        double maxLng = max.path("longitude").asDouble();

        double[] lat = {minLat, maxLat};
        double[] lng = {minLng, maxLng};

        // 화장실 위치 목록 가져오기
        List<ToiletDTO> toilets = toiletService.getNearToiletList(lat, lng);

        // 평균 평점 및 리뷰 개수 추가
        toilets.forEach(toilet -> {
            Map<String, Object> ratingData = toiletService.getRatingAndReviewCount(toilet.getId());
            toilet.setAverageRating((double) ratingData.getOrDefault("averageRating", 0.0));
            toilet.setReviewCount((int) ratingData.getOrDefault("reviewCount", 0));
        });

        return toilets;
    }

    @PostMapping("toilet/latandlng")
    @ResponseBody
    public double[] getLatAndLng(@RequestParam String address){
        if (address == null || address.isEmpty() || address.equals("null")){
            return new double[0];
        }
        return toiletService.getLatitudeAndLongitudeAsDouble(address);
    }

    @GetMapping("/toilet/detail/{id}")
    public String showToiletDetail(Model model, @PathVariable int id) {
        try {
            ToiletDTO toiletDTO = toiletService.getToilet(id);
            if (toiletDTO != null) {
                // 평균 평점 및 리뷰 갯수 가져오기
                Map<String, Object> ratingData = toiletService.getRatingAndReviewCount((long) id);

                // 평점 반올림 처리 (소수점 둘째 자리까지)
                double averageRating = (double) ratingData.getOrDefault("averageRating", 0.0);
                averageRating = Math.round(averageRating * 100.0) / 100.0;

                int reviewCount = (int) ratingData.getOrDefault("reviewCount", 0);

                model.addAttribute("toilet", toiletDTO);
                model.addAttribute("averageRating", averageRating); // 반올림된 평점
                model.addAttribute("reviewCount", reviewCount);

                return "toilet/detailView";
            } else {
                model.addAttribute("errorMessage", "해당 화장실 정보를 찾을 수 없습니다.");
                return "error"; // 사용자 정의 에러 페이지
            }
        } catch (Exception e) {
            // 예외 발생 시 로그 출력 및 에러 페이지로 이동
            System.err.println("Error fetching toilet details: " + e.getMessage());
            model.addAttribute("errorMessage", "서버 내부 오류가 발생했습니다.");
            return "error";
        }
    }

}
