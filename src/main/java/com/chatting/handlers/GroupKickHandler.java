package com.chatting.handlers;

import com.chatting.daos.JoinDAO;
import com.chatting.daos.RoomDAO;
import com.chatting.engine.Connection;
import com.chatting.engine.Server;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.message.GroupKickMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GroupKickHandler implements Handlable {
    @Override
    public void execute(Server server, Connection connection, String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            GroupKickMessage message = mapper.readValue(msg, GroupKickMessage.class);
            JoinDAO dao = new JoinDAO();
            RoomDAO roomDAO = new RoomDAO();
            if (roomDAO.isGroup(message.room) && !message.equals(connection.getSession().getUsername())) {
                if (dao.isExist(message.username, message.room)) {
                    new JoinDAO().removeByRoomAndUser(message.room, message.username);
                    connection.send(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
