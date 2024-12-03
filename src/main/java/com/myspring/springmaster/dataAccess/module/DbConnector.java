package com.myspring.springmaster.dataAccess.module;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbConnector {
    public static Connection connect() throws ClassNotFoundException, SQLException {
        return null;
    }
}
