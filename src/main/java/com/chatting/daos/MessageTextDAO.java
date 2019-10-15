package com.chatting.daos;


import com.chatting.dtos.MessageDTO;
import com.chatting.dtos.MessageTextDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageTextDAO extends DAO {
    public MessageTextDAO() {
        this.table = "[MessageText]";
    }
    public long create(int id_join, int id_room, String content, String username) {
        Date d = new Date();
        java.sql.Date date = new java.sql.Date(d.getTime());
        String SQL = this.injectTableName("INSERT INTO %s (id_join, content, time, id_room, username ) VALUES (?, ?, ?, ?, ?)");
        long id = -1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id_join);
            preparedStatement.setString(2, content);
            preparedStatement.setDate(3, date);
            preparedStatement.setInt(4, id_room);
            preparedStatement.setString(5, username);
            int affected = preparedStatement.executeUpdate();
            id = this.getCreatedId(affected, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    public List<MessageDTO> getByRoomId(int id_room) {
        List<MessageDTO> res = new ArrayList<>();
        String SQL = this.injectTableName("SELECT * FROM  %s  WHERE id_room = ?");
        try {
            PreparedStatement pr = connection.prepareStatement(SQL);
            pr.setInt(1, id_room);
            ResultSet rs = pr.executeQuery();
            while(rs.next()) {
                res.add(MessageTextDTO.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
