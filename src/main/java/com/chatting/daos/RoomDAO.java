package com.chatting.daos;


import com.chatting.dtos.JoinDTO;
import com.chatting.dtos.RoomDTO;
import com.chatting.exceptions.RecordNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class RoomDAO extends DAO {
    public RoomDAO() {
        this.table = "[Room]";
    }

    public List<RoomDTO> getFromUsername(String username) {
        List<JoinDTO> joins = new JoinDAO().findByUsername(username);
        Set<Integer> set = new HashSet<>();
        joins.forEach(join -> set.add(join.getId_room()));
        return set.stream().map(id -> {

            try {
                return this.getFromId(id);
            } catch (RecordNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public RoomDTO getFromId(int id) throws RecordNotFoundException {
        String SQL = this.injectTableName("SELECT TOP 1 * FROM %s WHERE id=?");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return RoomDTO.fromModel(rs);
        } catch (SQLException e) {
            throw new RecordNotFoundException();
        }
    }

    public long createSelfRoom(String creator) {
        List<String> members = new ArrayList<>();
        members.add(creator);
        members.add(creator);
        return this.createInbox(creator, members, "Yourself");
    }

    public long createInbox(String creator, List<String> members, String roomName) {
        long id = this.create(creator, "inbox", roomName);
        createJoin(id, members, creator);
        return id;
    }

    private void createJoin(long roomId, List<String> members, String creator) {
        JoinDAO joinDAO = new JoinDAO();
        members.forEach(mem -> {
            joinDAO.create(roomId, mem, creator);
        });
    }

    private long create(String creator, String type, String roomName) {
        String SQL = this.injectTableName("INSERT INTO %s (creator, type, time, update_time, group_IP) VALUES (?, ?, ?, ?, ?)");
        long id = -1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            Date date = new Date();
            preparedStatement.setString(1, creator);
            preparedStatement.setString(2, type);
            preparedStatement.setDate(3, new java.sql.Date(date.getTime()));
            preparedStatement.setDate(4, new java.sql.Date(date.getTime()));
            preparedStatement.setString(5, roomName);
            int affected = preparedStatement.executeUpdate();
            id = this.getCreatedId(affected, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}
