package com.chatting.handlers;

import com.chatting.daos.JoinDAO;
import com.chatting.daos.RoomDAO;
import com.chatting.engine.Connection;
import com.chatting.engine.Server;
import com.chatting.engine.Session;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.message.GroupInviteMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GroupInviteHandler implements Handlable {
    @Override
    public void execute(Server server, Connection connection, String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            GroupInviteMessage message = mapper.readValue(msg, GroupInviteMessage.class);
            JoinDAO dao = new JoinDAO();
            RoomDAO roomDAO = new RoomDAO();
            if (roomDAO.isGroup(message.room)) {
                if (dao.isExist(message.username, message.room)) {
                    return;
                }
                dao.create(message.room, message.username, connection.getSession().getUsername());
                Session session = server.getSession(message.username);
                String _temp = mapper.writeValueAsString(message);
                if(session != null) {
                    session.send(_temp);
                }
                server.getRoom(String.valueOf(message.room)).send(_temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
