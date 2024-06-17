package com.myspring.springmaster.dataAccess.DAO;

import com.myspring.springmaster.dataAccess.DTO.HouseDTO;
import com.myspring.springmaster.dataAccess.module.MysqlConnector;
import com.myspring.springmaster.dataAccess.module.TypeCast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HouseDAO {
    public ArrayList<HouseDTO> getHousesInfo(int num) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnector.connect();
        TypeCast typeCast = new TypeCast();
        PreparedStatement ps = conn.prepareStatement("select * from house limit ?");
        ps.setInt(1, num);
        ResultSet rs = ps.executeQuery();
        return typeCast.resultSetToDtoList(rs, HouseDTO.class);
    }

    public HouseDTO getHouseInfo(int idx) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnector.connect();
        TypeCast typeCast = new TypeCast();
        PreparedStatement ps = conn.prepareStatement("select * from house where idx = ?");
        ps.setInt(1, idx);
        ResultSet rs = ps.executeQuery();
        return typeCast.resultSetToDtoList(rs, HouseDTO.class).get(0);
    }

    public boolean addHouse(HouseDTO house) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnector.connect();
        PreparedStatement ps = conn.prepareStatement(
                "insert into house('name', 'description', 'price', 'count'," +
                        "'address', 'imageUrl', 'status', 'movingDate', 'subMissionDate') " +
                        "values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, house.getName());
        ps.setString(2, house.getDescription());
        ps.setDouble(3, house.getPrice());
        ps.setInt(4, house.getCount());
        ps.setString(5, house.getAddress());
        ps.setString(6, house.getImageUrl());
        ps.setString(7, house.getStatus());
        ps.setString(8, house.getMovingDate());
        ps.setString(9, house.getSubMissionDate());
        return ps.executeUpdate() > 0;
    }

}
