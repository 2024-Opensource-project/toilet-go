package com.myspring.springmaster.dataAccess.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myspring.springmaster.dataAccess.entity.HouseDetail;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TypeCast {
    public <E> ArrayList<E> resultSetToDtoList(ResultSet rs, Class<E> dto) throws SQLException, ClassNotFoundException {
        ResultSetMetaData data = rs.getMetaData();
        ArrayList<E> rtnValue =  new ArrayList<>();
        Gson gson = new Gson();
        while (rs.next()) {
            HashMap<String, Object> map = new HashMap<>();
            for(int indexOfColumn = 1; indexOfColumn <= data.getColumnCount(); indexOfColumn++) {
                String column = data.getColumnName(indexOfColumn);
                map.put(column, rs.getString(column));
            }
            rtnValue.add(gson.fromJson(gson.toJson(map), dto));
        }
        return rtnValue;
    }

}
