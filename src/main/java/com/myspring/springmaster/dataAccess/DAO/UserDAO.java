package com.myspring.springmaster.dataAccess.DAO;

import com.myspring.springmaster.dataAccess.DTO.UserDTO;
import com.myspring.springmaster.dataAccess.module.MysqlConnector;
import com.myspring.springmaster.dataAccess.module.TypeCast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    public boolean isExistIdAndPw(String id, String pw) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnector.connect();
        PreparedStatement ps = conn.prepareStatement("select * from members where user_id = ? and pw = ?");
        ps.setString(1, id);
        ps.setString(2, pw);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public boolean findById(String id) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnector.connect();
        PreparedStatement ps = conn.prepareStatement("select * from members where user_id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public void save(UserDTO user) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnector.connect();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO members(user_id, pw, name, email) VALUES (?, ?, ?, ?)");
        ps.setString(1, user.getUserId());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getName());
        ps.setString(4, user.getEmail());
        ps.executeUpdate();
        conn.close();
    }
}
