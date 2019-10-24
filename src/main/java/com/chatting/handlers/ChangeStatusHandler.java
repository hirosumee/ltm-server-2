package com.chatting.handlers;

import com.chatting.engine.Connection;
import com.chatting.engine.Server;
import com.chatting.engine.SessionStatus;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.message.ChangeStatusMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ChangeStatusHandler implements Handlable {
    @Override
    public void execute(Server server, Connection connection, String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ChangeStatusMessage message = mapper.readValue(msg, ChangeStatusMessage.class);
            String status = message.status;
            if (status.equals(SessionStatus.offline) ||
                    status.equals(SessionStatus.idle) ||
                    status.equals(SessionStatus.online)) {
                connection.getSession().setPersistStatus(message.status);
                connection.send(mapper.writeValueAsString(message));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
