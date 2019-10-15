package com.chatting.daos;


import com.chatting.dtos.JoinDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class JoinDAO extends DAO {
    public JoinDAO() {
        this.table = "[Join]";
    }
    public long create(long roomId, String username, String adder) {
        String SQL = this.injectTableName("INSERT INTO %s (username, id_room, time, adder) VALUES (?, ?, ?, ?)");
        long id = -1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(2, roomId);
            preparedStatement.setString(1, username);
            preparedStatement.setDate(3,new java.sql.Date(new Date().getTime()));
            preparedStatement.setString(4, adder);
            int affectedRows = preparedStatement.executeUpdate();
            id = this.getCreatedId(affectedRows, preparedStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
    public ArrayList<JoinDTO> findByUsername(String username) {
        ArrayList<JoinDTO> list = new ArrayList<>();
        String SQL = this.injectTableName("SELECT *  FROM %s WHERE username = ?");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                list.add(JoinDTO.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<JoinDTO> findByUsernameAndRoom(String username, int id_room) {
        ArrayList<JoinDTO> list = new ArrayList<>();
        String SQL = this.injectTableName("SELECT *  FROM %s WHERE username = ? AND id_room = ?");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, id_room);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                list.add(JoinDTO.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<JoinDTO> findByRoom(int id_room) {
        ArrayList<JoinDTO> list = new ArrayList<>();
        String SQL = this.injectTableName("SELECT *  FROM %s WHERE id_room = ?");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id_room);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                list.add(JoinDTO.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public int removeByRoomAndUser(int id, String username) {
        String SQL = this.injectTableName("DELETE FROM %s WHERE id_room = ? AND username = ?");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, username);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
