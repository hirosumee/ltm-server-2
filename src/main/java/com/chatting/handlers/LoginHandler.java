package com.chatting.handlers;

import com.chatting.engine.Connection;
import com.chatting.engine.Server;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.message.LoginMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class LoginHandler implements Handlable {
    @Override
    public void execute(Server server, Connection connection, String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoginMessage message = mapper.readValue(msg, LoginMessage.class);
            System.out.println(message.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
