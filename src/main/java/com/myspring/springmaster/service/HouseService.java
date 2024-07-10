package com.myspring.springmaster.service;

import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.entity.House;
import com.myspring.springmaster.dataAccess.mapper.HouseMapper;
import com.myspring.springmaster.dataAccess.repository.HouseRepository;
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

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    @Autowired
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }
/*
    public ArrayList<HouseDTO> getHouseList() throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        return houseDAO.getHousesInfo();
    }
*/
    public HouseDTO getHouse(int id) {
        House house = houseRepository.findById((long) id).orElseThrow();
        return HouseMapper.Instance.toDTO(house);
    }

    public List<HouseDTO> getAllHouses() {
        List<House> houses = houseRepository.findAll();
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

    public List<HouseDTO> getAllActiveHousesList() {
        final String ACTIVE_HOUSE_STRING = "공고중";
        List<House> houseList = houseRepository.findAllByStatus(ACTIVE_HOUSE_STRING);
        List<HouseDTO> housesDTO = new ArrayList<>();
        for (House house : houseList) {
            housesDTO.add(HouseMapper.Instance.toDTO(house));
        }
        return housesDTO;
    }

    public List<HouseDTO> getNearHouseList(String address) {
        List<HouseDTO> houseDTOList = this.getAllHouses();
        BigDecimal[] latAndLong = getLatitudeAndLongitude(address);
        List<HouseDTO> nearHouseDTOList = new ArrayList<>();
        if(latAndLong != null) {
            nearHouseDTOList = houseDTOList.stream()
                    .filter(house -> isNear(house, latAndLong))
                    .toList();
        }

        return nearHouseDTOList;
    }
/*
    public ArrayList<HouseDTO> getHousesListBySearch(HouseDTO houseDTO) throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        return null; //여기 고치기
    }

    public List<HouseDTO> getHouseList(String areaName) throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        ArrayList<HouseDTO> houseList = houseDAO.getAllActiveHousesInfo();
        List<HouseDTO> houses = houseList.stream()
                .filter(x -> isNear(x.getAddress(), areaName))
                .toList();
        return houses;
    }

    public ArrayList<HouseDTO> getSameAreaHouses(String address) throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        return houseDAO.getSameAreaHouses(address);
    }


*/
    private void removeUselessWord(HouseDTO house) {
        String[] uselessWords = {"소재지 :", "지도보기"};
        String address = house.getAddress();
        for(String word : uselessWords){
            address = address.replace(word, "");
        }
        house.setAddress(address.strip());
    }

    private BigDecimal[] getLatitudeAndLongitude(String address){
        BigDecimal[] rtnValue = new BigDecimal[2];
        try {
            ProcessBuilder pb = new ProcessBuilder("python",
                    "C:\\Users\\guna\\Desktop\\springMaster\\zzzz\\naver_map_api.py", address);
            Process p = pb.start();

            // 파이썬 스크립트의 출력 읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String result = in.readLine();
            if(result.equals("Not Found") || result == null){ //오류처리 해보기
                return null;
            }
            String[] results = result.split(",");
            rtnValue[0] = BigDecimal.valueOf(Double.parseDouble(results[0]));
            rtnValue[1] = BigDecimal.valueOf(Double.parseDouble(results[1]));

            in.close();
            // 파이썬 스크립트의 종료 코드 확인
            int exitCode = p.waitFor();
            if (exitCode != 0) {
                System.out.println("파이썬 스크립트 실행 실패");
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnValue;
    }


    private boolean isNear(HouseDTO houseDTO, String address){
        BigDecimal[] latAndLon1 = {houseDTO.getLatitude(), houseDTO.getLongitude()};
        BigDecimal[] latAndLon2 = getLatitudeAndLongitude(address);
        double distance = calcDistance(latAndLon1, latAndLon2);
        System.out.println(distance);
        return distance<5;
    }

    private boolean isNear(HouseDTO houseDTO, BigDecimal[] latAndLon2){
        BigDecimal[] latAndLong1 = {houseDTO.getLatitude(), houseDTO.getLongitude()};
        double distance = calcDistance(latAndLong1, latAndLon2);
        System.out.println(Arrays.toString(latAndLong1) +","+Arrays.toString(latAndLon2));
        System.out.println(distance);
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        return distance<5;
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
