package com.myspring.springmaster.service;

import com.myspring.springmaster.dataAccess.entity.Favorite;
import com.myspring.springmaster.dataAccess.entity.User;
import com.myspring.springmaster.dataAccess.entity.Toilet;
import com.myspring.springmaster.dataAccess.repository.FavoriteRepository;
import com.myspring.springmaster.dataAccess.repository.UserRepository;
import com.myspring.springmaster.dataAccess.repository.ToiletRepository;
import com.myspring.springmaster.dataAccess.DTO.FavoriteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ToiletRepository toiletRepository;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository, ToiletRepository toiletRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.toiletRepository = toiletRepository;
    }

    // 즐겨찾기 추가
    public Favorite addFavorite(FavoriteDTO favoriteDTO) {
        try {
            // 사용자 객체 조회
            User user = userRepository.findById(favoriteDTO.getUserId());
            if (user == null) {
                throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
            }

            // 화장실 객체 조회
            Toilet toilet = toiletRepository.findById(Long.valueOf(favoriteDTO.getToiletId()))
                    .orElseThrow(() -> new IllegalArgumentException("화장실을 찾을 수 없습니다."));

            // 이미 즐겨찾기 목록에 존재하는지 확인
            favoriteRepository.findByUserAndToilet(user, toilet)
                    .ifPresent(existingFavorite -> {
                        throw new IllegalStateException("이미 즐겨찾기에 등록된 화장실입니다.");
                    });

            // 즐겨찾기 등록
            Favorite favorite = new Favorite(user, toilet);
            return favoriteRepository.save(favorite);
        } catch (Exception e) {
            throw new RuntimeException("즐겨찾기 추가 중 오류 발생: " + e.getMessage(), e);
        }
    }
    // 사용자가 즐겨찾기한 화장실 목록 조회
    public List<FavoriteDTO> getFavoritesByUserId(Integer userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
        List<Favorite> favorites = favoriteRepository.findByUser(user);
        List<FavoriteDTO> favoriteDTOs = new ArrayList<>();
        for (Favorite favorite : favorites) {
            FavoriteDTO favoriteDTO = new FavoriteDTO();
            favoriteDTO.setUserId(Math.toIntExact(favorite.getUser().getId()));
            favoriteDTO.setToiletId(Math.toIntExact(favorite.getToilet().getId()));
            favoriteDTOs.add(favoriteDTO);
        }

        return favoriteDTOs;
    }

    // 즐겨찾기 삭제
    public void removeFavorite(FavoriteDTO favoriteDTO) {
        try {
            // 사용자 객체 조회
            User user = userRepository.findById(favoriteDTO.getUserId());
            if (user == null) {
                throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
            }

            // 화장실 객체 조회
            Toilet toilet = toiletRepository.findById(Long.valueOf(favoriteDTO.getToiletId()))
                    .orElseThrow(() -> new IllegalArgumentException("화장실을 찾을 수 없습니다."));

            // 이미 즐겨찾기 목록에 존재하는지 확인
            Favorite favorite = favoriteRepository.findByUserAndToilet(user, toilet)
                    .orElseThrow(() -> new IllegalStateException("즐겨찾기에서 찾을 수 없는 화장실입니다."));

            // 즐겨찾기 삭제
            favoriteRepository.delete(favorite);
        } catch (Exception e) {
            throw new RuntimeException("즐겨찾기 삭제 중 오류 발생: " + e.getMessage(), e);
        }




    }
}
