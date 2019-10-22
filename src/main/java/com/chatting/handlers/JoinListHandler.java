package com.chatting.handlers;

import com.chatting.daos.JoinDAO;
import com.chatting.engine.Connection;
import com.chatting.engine.Server;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.message.JoinListMessage;
import com.chatting.message.JoinListMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class JoinListHandler implements Handlable {
    @Override
    public void execute(Server server, Connection connection, String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JoinListMessage message = mapper.readValue(msg, JoinListMessage.class);
            ArrayList<String> usernames = (ArrayList<String>) new JoinDAO().findByRoom(message.room).stream().map(i -> i.getUsername()).collect(Collectors.toList());
            connection.send(mapper.writeValueAsString(new JoinListMessageResponse(usernames)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
