package com.myspring.springmaster.service;

import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.entity.House;
import com.myspring.springmaster.dataAccess.mapper.HouseMapper;
import com.myspring.springmaster.dataAccess.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

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

    public boolean addHouse(HouseDTO houseDTO) {
        removeUselessWord(houseDTO);
        /*double[] latiAndLong = this.getLatitudeAndLongitude(house.getAddress());
        if(latiAndLong != null) {
            house.setLatitude(latiAndLong[0]);
            house.setLongitude(latiAndLong[1]);
        }*/
        House house = HouseMapper.Instance.toEntity(houseDTO);
        houseRepository.save(house);
        return true;
    }
/*
    public ArrayList<HouseDTO> getAllActiveHousesList() throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        ArrayList<HouseDTO> houseList = houseDAO.getAllActiveHousesInfo();
        return houseList;
    }

    public List<HouseDTO> getNearHouseList(String address) throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        ArrayList<HouseDTO> houseList = houseDAO.getAllActiveHousesInfo();
        String[] latAndLong = getLatitudeAndLongitude(address);
        List<HouseDTO> houses = houseList.stream()
                            .filter(house -> isNear(house, latAndLong))
                            .toList();
        return houses;
    }

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

    private double[] getLatitudeAndLongitude(String address){
        double[] rtnValue = new double[2];
        try {
            ProcessBuilder pb = new ProcessBuilder("C:\\Users\\guna\\Desktop\\springMaster\\venv\\Scripts\\python.exe",
                    "C:\\Users\\guna\\Desktop\\springMaster\\zzzz\\naver_map_api.py", address);
            Process p = pb.start();

            // 파이썬 스크립트의 출력 읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String result = in.readLine();
            if(result.equals("Not Found") || result == null){ //오류처리 해보기
                return null;
            }
            String[] results = result.split(",");
            rtnValue[0] = Double.parseDouble(results[0]);
            rtnValue[1] = Double.parseDouble(results[1]);

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

    /*
    private boolean isNear(HouseDTO houseDTO, String address){
        String[] latAndLon1 = {houseDTO.getLatitude(), houseDTO.getLongitude()};
        String[] latAndLon2 = getLatitudeAndLongitude(address);
        double distance = calcDistance(latAndLon1, latAndLon2);
        System.out.println(distance);
        return distance<5;
    }

    private boolean isNear(HouseDTO houseDTO, String[] latAndLon2){
        String[] latAndLong1 = {houseDTO.getLatitude(), houseDTO.getLongitude()};
        double distance = calcDistance(latAndLong1, latAndLon2);
        System.out.println(Arrays.toString(latAndLong1) +","+Arrays.toString(latAndLon2));
        System.out.println(distance);
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        return distance<5;
    }
*/
    private double[] convertToDoubleArray(String[] arr) throws NumberFormatException {
        return Arrays.stream(arr)
                .mapToDouble(Double::parseDouble)
                .toArray();
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

    private double calcDistance(String[] latAndLon1, String[] latAndLon2){
        return this.calcDistance(convertToDoubleArray(latAndLon1), convertToDoubleArray(latAndLon2));
    }
}
