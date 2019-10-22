package com.chatting.handlers;

import com.chatting.engine.Connection;
import com.chatting.engine.Server;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.message.UserStatusMessage;
import com.chatting.message.UserStatusResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class UserStatusHandler implements Handlable {

    @Override
    public void execute(Server server, Connection connection, String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserStatusMessage message = mapper.readValue(msg, UserStatusMessage.class);
            String status = server.getSessionStatus(message.username);
            connection.send(mapper.writeValueAsString(new UserStatusResponse(message.username, status)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
