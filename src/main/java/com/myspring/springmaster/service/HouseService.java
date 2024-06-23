package com.myspring.springmaster.service;

import com.myspring.springmaster.dataAccess.DAO.HouseDAO;
import com.myspring.springmaster.dataAccess.DTO.HouseDTO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HouseService {
    public ArrayList<HouseDTO> getHouseList() throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        return houseDAO.getHousesInfo();
    }

    public HouseDTO getHouse(int idx) throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        return houseDAO.getActiveHouseInfo(idx);
    }

    public List<HouseDTO> getNearHouseList(String address) throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        ArrayList<HouseDTO> houseList = houseDAO.getAllActiveHousesInfo();
        List<HouseDTO> houses = houseList.stream()
                            .filter(x -> isNear(x.getAddress(), address))
                            .toList();
        return houses;
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

    public boolean addHouse(HouseDTO house) throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        removeUselessWord(house);
        String[] latiAndLong = this.getLatitudeAndLongitude(house.getAddress());
        if(latiAndLong != null) {
            house.setLatitude(latiAndLong[0]);
            house.setLongitude(latiAndLong[1]);
        }
        if(!houseDAO.isAlreadyUploadedHouse(house)){
            return houseDAO.addHouse(house);
        }
        return false;
    }

    private void removeUselessWord(HouseDTO house) {
        String[] uselessWords = {"소재지 :", "지도보기"};
        String address = house.getAddress();
        for(String word : uselessWords){
            address = address.replace(word, "");
        }
        house.setAddress(address.strip());
    }

    private String[] getLatitudeAndLongitude(String address){
        String[] rtnValues = new String[2];
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
            rtnValues = result.split(",");


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
        return rtnValues;
    }


    private boolean isNear(String houseAddress, String address){
        //미구현
        return true;
    }
}
