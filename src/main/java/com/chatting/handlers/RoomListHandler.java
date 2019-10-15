package com.chatting.handlers;

import com.chatting.daos.RoomDAO;
import com.chatting.dtos.RoomDTO;
import com.chatting.dtos.UserDTO;
import com.chatting.engine.Connection;
import com.chatting.engine.Room;
import com.chatting.engine.Server;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.message.RoomListResponseMessage;
import com.chatting.utils.RoomListUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class RoomListHandler implements Handlable {
    @Override
    public void execute(Server server, Connection connection, String msg) {
        UserDTO user = (UserDTO) connection.getSession().getUser();
        List<RoomDTO> list = new RoomDAO().getFromUsername(user.getUsername());
        joinAll(list, server, connection);
        ObjectMapper mapper = new ObjectMapper();
        RoomListResponseMessage res = RoomListUtils.fromRoomList(list);
        try {
            connection.send(mapper.writeValueAsString(res));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    private void joinAll(List<RoomDTO> list, Server server, Connection connection) {
        list.forEach(i -> {
            Room room = server.addRoom(String.valueOf(i.getId()));
            room.join(connection);
        });
    }
}
