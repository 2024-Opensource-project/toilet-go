package com.myspring.springmaster.dataAccess.module;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnector implements DbConnector {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/spring_project";
    private static final String USER = "root";
    private static final String PW = "myProject!";
    private static Connection conn;

    public static Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(URL, USER, PW);
        return conn;
    }
}
