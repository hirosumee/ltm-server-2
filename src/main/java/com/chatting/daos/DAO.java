package com.chatting.daos;


import com.chatting.vendor.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract class DAO {
    String table;
    Connection connection;

    {
        try {
            connection = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String injectTableName(String str) {
        return String.format(str, table);
    }
    long getCreatedId(long affected, PreparedStatement preparedStatement) {
        if (affected > 0) {
            // get the ID back
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                return -1;
            }
        }
        return -1;
    }
}
