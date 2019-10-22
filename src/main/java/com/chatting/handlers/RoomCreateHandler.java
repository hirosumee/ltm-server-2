package com.chatting.handlers;

import com.chatting.daos.RoomDAO;
import com.chatting.dtos.UserDTO;
import com.chatting.engine.Connection;
import com.chatting.engine.Server;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.message.GroupCreateMessage;
import com.chatting.message.RoomCreateMessage;
import com.chatting.message.RoomCreatedMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class RoomCreateHandler implements Handlable {
    @Override
    public void execute(Server server, Connection connection, String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            RoomCreateMessage message = mapper.readValue(msg, RoomCreateMessage.class);
            String username = connection.getSession().getUsername();
            ArrayList<String> members = new ArrayList<>();
            members.add(username);
            members.add(message.username);
            String name = message.name;
            new RoomDAO().createInbox(username, members, name);
            connection.send(mapper.writeValueAsString(new RoomCreatedMessage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
