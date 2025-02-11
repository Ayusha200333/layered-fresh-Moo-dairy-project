package com.example.finalproject.dao;

import com.example.finalproject.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {
    public static <T> T execute( String sql,Object... params ) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject((i + 1), params[i]);
        }
       if (sql.startsWith("SELECT") || sql.startsWith("select")) {
           return (T) preparedStatement.executeQuery();
       }else{
           return (T) (Boolean) (preparedStatement.executeUpdate()>0);
       }

    }

}
