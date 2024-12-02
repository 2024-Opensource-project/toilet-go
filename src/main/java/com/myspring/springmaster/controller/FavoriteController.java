package com.myspring.springmaster.controller;

import com.myspring.springmaster.dataAccess.DTO.FavoriteDTO;
import com.myspring.springmaster.dataAccess.entity.Favorite;
import com.myspring.springmaster.service.FavoriteService;
import com.myspring.springmaster.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FavoriteController {

    @Autowired
    private final FavoriteService favoriteService;
    private final UserService userService;

    public FavoriteController(FavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    // 즐겨찾기 목록 조회
    @GetMapping("/favorites/load")
    public ResponseEntity<List<FavoriteDTO>> getFavorites(HttpSession session) {
        Integer userId = null;
        Object sessionId = session.getAttribute("id");
        if (sessionId instanceof Long) {
            userId = ((Long) sessionId).intValue();  // Long -> Integer로 변환
        } else if (sessionId instanceof Integer) {
            userId = (Integer) sessionId;
        }
        List<FavoriteDTO> favorites = favoriteService.getFavoritesByUserId(userId);
        return ResponseEntity.ok(favorites);
    }

    // 즐겨찾기 추가
    @PostMapping("/favorites/add")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addFavorite(@RequestBody FavoriteDTO favorite, HttpSession session) {
        Integer userId = null;
        Object sessionId = session.getAttribute("id");
        if (sessionId instanceof Long) {
            userId = ((Long) sessionId).intValue();  // Long -> Integer로 변환
        } else if (sessionId instanceof Integer) {
            userId = (Integer) sessionId;
        }
        favorite.setUserId(userId);

        if (userService.isLoggedIn(session)) {  // 인증된 사용자만 추가 가능
            try {
                favoriteService.addFavorite(favorite);  // 즐겨찾기 추가 로직
                Map<String, String> response = new HashMap<>();
                response.put("message", "Favorite added successfully");
                return ResponseEntity.ok(response); // JSON 형식으로 응답
            } catch (Exception e) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Error adding favorite: " + e.getMessage());
                return ResponseEntity.status(500).body(response); // JSON 형식으로 응답
            }
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "You need to be logged in to add to favorites.");
        return ResponseEntity.status(403).body(response); // JSON 형식으로 응답
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/favorites/remove")
    public ResponseEntity<Map<String, String>> removeFavorite(@RequestBody FavoriteDTO favorite, HttpSession session) {
        Integer userId = null;
        Object sessionId = session.getAttribute("id");
        if (sessionId instanceof Long) {
            userId = ((Long) sessionId).intValue();  // Long -> Integer로 변환
        } else if (sessionId instanceof Integer) {
            userId = (Integer) sessionId;
        }
        favorite.setUserId(userId);
        if (userService.isLoggedIn(session)) {  // 인증된 사용자만 추가 가능
            try {
                favoriteService.removeFavorite(favorite); // 즐겨찾기 삭제 로직
                Map<String, String> response = new HashMap<>();
                response.put("message", "Favorite removed successfully");
                return ResponseEntity.ok(response); // JSON 형식으로 응답
            } catch (Exception e) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Error removing favorite: " + e.getMessage());
                return ResponseEntity.status(500).body(response); // JSON 형식으로 응답
            }
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "You need to be logged in to remove to favorites.");
        return ResponseEntity.status(403).body(response); // JSON 형식으로 응답
    }

}
