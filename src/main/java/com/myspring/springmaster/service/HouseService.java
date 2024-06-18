package com.myspring.springmaster.service;

import com.myspring.springmaster.dataAccess.DAO.HouseDAO;
import com.myspring.springmaster.dataAccess.DTO.HouseDTO;

import java.sql.SQLException;
import java.util.ArrayList;
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
        return houseDAO.addHouse(house);
    }


    private boolean isNear(String houseAddress, String address){
        //미구현
        return true;
    }
}
