package com.myspring.springmaster.dataAccess.DAO;

import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.module.MysqlConnector;
import com.myspring.springmaster.dataAccess.module.TypeCast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HouseDAO {/*
    public ArrayList<HouseDTO> getHousesInfo(int num) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnector.connect();
        TypeCast typeCast = new TypeCast();
        PreparedStatement ps = conn.prepareStatement("select * from house limit ?");
        ps.setInt(1, num);
        ResultSet rs = ps.executeQuery();
        return typeCast.resultSetToDtoList(rs, HouseDTO.class);
    }

    public ArrayList<HouseDTO> getHousesInfo() throws SQLException, ClassNotFoundException {
        final int numOfHouses = 10;
        return  this.getHousesInfo(numOfHouses);
    }

    public HouseDTO getActiveHouseInfo(int idx) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnector.connect();
        TypeCast typeCast = new TypeCast();
        PreparedStatement ps = conn.prepareStatement("select * from house where idx = ?");
        ps.setInt(1, idx);
        ResultSet rs = ps.executeQuery();
        return typeCast.resultSetToDtoList(rs, HouseDTO.class).get(0);
    }

    public ArrayList<HouseDTO> getAllActiveHousesInfo() throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnector.connect();
        TypeCast typeCast = new TypeCast();
        PreparedStatement ps = conn.prepareStatement("select * from house");
        ResultSet rs = ps.executeQuery();
        return typeCast.resultSetToDtoList(rs, HouseDTO.class);
    }

    public ArrayList<HouseDTO> getSameAreaHouses(String areaName) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnector.connect();
        TypeCast typeCast = new TypeCast();
        PreparedStatement ps = conn.prepareStatement("select * from house where address like ?");
        ps.setString(1, "%"+areaName+"%");
        ResultSet rs = ps.executeQuery();
        return typeCast.resultSetToDtoList(rs, HouseDTO.class);
    }

    public ArrayList<HouseDTO> getHousesList(HouseDTO h) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnector.connect();
        TypeCast typeCast = new TypeCast();
        PreparedStatement ps = conn.prepareStatement("select * from house where name like ? AND address like ? ");
        return null; //여기 고치기
    }

    public boolean isAlreadyUploadedHouse(HouseDTO house) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnector.connect();
        PreparedStatement ps = conn.prepareStatement
                ("select * from house where name = ? and address = ? and submissionDate = ?");
        ps.setString(1, house.getName());
        ps.setString(2, house.getAddress());
        ps.setString(3, house.getSubmissionDate());
        ResultSet rs = ps.executeQuery();
        return rs.next();

    }
    public boolean addHouse(HouseDTO house) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnector.connect();
        PreparedStatement ps = conn.prepareStatement(
                "insert into house(`name`, `description`, `price`, `count`," +
                        "`address`, `latitude`, `longitude`, `imageUrl`, `status`, `movingDate`, `submissionDate`) " +
                        "values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, house.getName());
        ps.setString(2, house.getDescription());
        ps.setString(3, house.getPrice());
        ps.setString(4, house.getCount());
        ps.setString(5, house.getAddress());
        ps.setString(6, house.getLatitude());
        ps.setString(7, house.getLongitude());
        ps.setString(8, house.getImageUrl());
        ps.setString(9, house.getStatus());
        ps.setString(10, house.getMovingDate());
        ps.setString(11, house.getSubmissionDate());
        return ps.executeUpdate() > 0;
    }
*/
}

