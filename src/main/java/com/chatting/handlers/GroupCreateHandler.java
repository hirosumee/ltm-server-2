package com.chatting.handlers;

import com.chatting.daos.RoomDAO;
import com.chatting.engine.Connection;
import com.chatting.engine.Server;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.message.GroupCreateMessage;
import com.chatting.message.GroupCreatedMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GroupCreateHandler implements Handlable {
    @Override
    public void execute(Server server, Connection connection, String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            GroupCreateMessage message = mapper.readValue(msg, GroupCreateMessage.class);
            String username = connection.getSession().getUsername();
            new RoomDAO().createGroup(username, message.name);
            connection.send(mapper.writeValueAsString(new GroupCreatedMessage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
