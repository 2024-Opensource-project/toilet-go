package com.myspring.springmaster.service;

import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.DTO.PreviewHouseDTO;
import com.myspring.springmaster.dataAccess.entity.House;
import com.myspring.springmaster.dataAccess.mapper.HouseMapper;
import com.myspring.springmaster.dataAccess.repository.HouseRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.myspring.springmaster.dataAccess.entity.QHouse.house;

@Service
public class HouseService {

    private final HouseRepository houseRepository;
    private final NaverMapApi naverMapApi;

    @Autowired
    public HouseService(HouseRepository houseRepository, NaverMapApi naverMapApi) {
        this.houseRepository = houseRepository;
        this.naverMapApi = naverMapApi;
    }


    public HouseDTO getHouse(int id) {
        House house = houseRepository.findById((long) id).orElse(null);
        return HouseMapper.Instance.toDTO(house);
    }

    public List<HouseDTO> getAllActiveHousesList() {
        final String ACTIVE_HOUSE_STRING = "공고중";
        List<House> houseList = houseRepository.findAllByStatusOrderByIdDesc(ACTIVE_HOUSE_STRING);
        List<HouseDTO> housesDTO = new ArrayList<>();
        for (House house : houseList) {
            housesDTO.add(HouseMapper.Instance.toDTO(house));
        }
        return housesDTO;
    }

    public List<HouseDTO> getHousesByFilter(HouseDTO filter) {
        List<House> houses = houseRepository.findAllByFilter(filter);
        List<HouseDTO> housesDTO = new ArrayList<>();
        for (House house : houses) {
            housesDTO.add(HouseMapper.Instance.toDTO(house));
        }
        return housesDTO;
    }


    public List<HouseDTO> getHouses(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<House> houses = houseRepository.findAll(pageable).getContent();
        List<HouseDTO> housesDTO = new ArrayList<>();
        for (House house : houses) {
            housesDTO.add(HouseMapper.Instance.toDTO(house));
        }
        return housesDTO;
    }

    public List<PreviewHouseDTO> getPreviewHouses(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<House> houses = houseRepository.findAll(pageable).getContent();
        List<PreviewHouseDTO> previewHouseDTOS = new ArrayList<>();
        for (House house : houses) {
            PreviewHouseDTO previewHouseDTO = new PreviewHouseDTO();
            previewHouseDTO.setId(house.getId());
            previewHouseDTO.setName(house.getName());
            previewHouseDTO.setAddress(house.getAddress());
            previewHouseDTO.setImageUrl(house.getHouseDetails().get(0).getImageUrl());
            previewHouseDTOS.add(previewHouseDTO);
        }
        return previewHouseDTOS;
    }

    public boolean addHouse(HouseDTO houseDTO) {
        removeUselessWord(houseDTO);
        BigDecimal[] latiAndLong = this.getLatitudeAndLongitude(houseDTO.getAddress());
        if(latiAndLong != null) {
            houseDTO.setLatitude(latiAndLong[0]);
            houseDTO.setLongitude(latiAndLong[1]);
        }
        House house = HouseMapper.Instance.toEntity(houseDTO);
        houseRepository.save(house);
        return true;
    }



    public List<HouseDTO> getNearHouseList(String address, int distance) {
        List<HouseDTO> houseDTOList = this.getAllActiveHousesList();
        BigDecimal[] latAndLong = getLatitudeAndLongitude(address);
        List<HouseDTO> nearHouseDTOList = new ArrayList<>();
        if(latAndLong != null) {
            nearHouseDTOList = houseDTOList.stream()
                    .filter(house -> isNear(house, latAndLong, distance))
                    .toList();
        }
        return nearHouseDTOList;
    }

    public List<double[]> getAllHousesLocation(){
        List<double[]> locationList = new ArrayList<>();
        List<HouseDTO> houses = this.getAllActiveHousesList();
        houses.forEach(house -> {
            double[] location = {house.getLatitude().doubleValue(), house.getLongitude().doubleValue()};
            locationList.add(location);
        });
        return locationList;
    }

    public double[] getLatitudeAndLongitudeAsDouble(String address){
        BigDecimal[] data = this.getLatitudeAndLongitude(address);
        double[] rtnValue = new double[2];
        if(data != null) {
            rtnValue[0] = data[0].doubleValue();
            rtnValue[1] = data[1].doubleValue();
        }
        return rtnValue;
    }

    private void removeUselessWord(HouseDTO house) {
        String[] uselessWords = {"소재지 :", "지도보기"};
        String address = house.getAddress();
        for(String word : uselessWords){
            address = address.replace(word, "");
        }
        house.setAddress(address.strip());
    }

    private BigDecimal[] getLatitudeAndLongitude(String address){
        return naverMapApi.getLatAndLng(address);
    }


    private boolean isNear(HouseDTO houseDTO, String address, int wantedDistance){
        BigDecimal[] latAndLon1 = {houseDTO.getLatitude(), houseDTO.getLongitude()};
        BigDecimal[] latAndLon2 = getLatitudeAndLongitude(address);
        double distance = calcDistance(latAndLon1, latAndLon2);
        return distance<wantedDistance;
    }

    private boolean isNear(HouseDTO houseDTO, BigDecimal[] latAndLon2, int wantedDistance){
        BigDecimal[] latAndLong1 = {houseDTO.getLatitude(), houseDTO.getLongitude()};
        return calcDistance(latAndLong1, latAndLon2) <= wantedDistance;
    }


    private double calcDistance(double[] latAndLon1, double[] latAndLon2){
        final double EARTH_RADIUS = 6371.0;
        double lat1Rad = Math.toRadians(latAndLon1[0]);
        double lon1Rad = Math.toRadians(latAndLon1[1]);
        double lat2Rad = Math.toRadians(latAndLon2[0]);
        double lon2Rad = Math.toRadians(latAndLon2[1]);

        // Haversine 공식 적용
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 지구의 반경을 곱하여 거리 계산
        return EARTH_RADIUS * c;

    }

    private double calcDistance(BigDecimal[] latAndLon1, BigDecimal[] latAndLon2){
        return this.calcDistance(
                Arrays.stream(latAndLon1).mapToDouble(BigDecimal::doubleValue).toArray(),
                Arrays.stream(latAndLon2).mapToDouble(BigDecimal::doubleValue).toArray());
    }
}
