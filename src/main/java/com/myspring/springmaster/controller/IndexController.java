package com.myspring.springmaster.controller;

import com.myspring.springmaster.dataAccess.DTO.PreviewHouseDTO;
import com.myspring.springmaster.service.HouseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String homePage(Model model, HttpSession session) {
        final int HOUSE_COUNT = 10;
        // 세션에서 로그인 여부와 사용자 정보를 가져옵니다.
        String userId = (String) session.getAttribute("userId");
        String userName = (String) session.getAttribute("name");

        // HOUSE_COUNT만큼 미리보기 집 목록을 가져옵니다.
        List<PreviewHouseDTO> houseList = houseService.getPreviewHouses(HOUSE_COUNT);

        // 로그인 상태일 때
        if (userId != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userName", userName); // 세션에서 가져온 사용자 이름
        } else {
            model.addAttribute("isLoggedIn", false);
        }

        // 집 목록을 모델에 추가
        model.addAttribute("houseList", houseList);

        return "index";  // index.html로 리턴
    }
    private void test(){
        return;
    }
}
