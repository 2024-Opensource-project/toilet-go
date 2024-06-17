package com.myspring.springmaster.service;

import com.myspring.springmaster.dataAccess.DAO.HouseDAO;
import com.myspring.springmaster.dataAccess.DTO.HouseDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class HouseService {
    public ArrayList<HouseDTO> getHouseList() throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        final int numOfHouses = 10;
        return houseDAO.getHousesInfo(numOfHouses);
    }

    public HouseDTO getHouse(int idx) throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        return houseDAO.getHouseInfo(idx);
    }

    public boolean addHouse(HouseDTO house) throws SQLException, ClassNotFoundException {
        HouseDAO houseDAO = new HouseDAO();
        return houseDAO.addHouse(house);
    }
}
