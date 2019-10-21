package com.chatting.handlers;

import com.chatting.daos.UserDAO;
import com.chatting.dtos.UserDTO;
import com.chatting.engine.Connection;
import com.chatting.engine.Server;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.message.UserFindMessage;
import com.chatting.message.UserFindResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserFindHandler implements Handlable {
    @Override
    public void execute(Server server, Connection connection, String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserFindMessage message = mapper.readValue(msg, UserFindMessage.class);
            UserFindResponseMessage response = handle();
            connection.send(mapper.writeValueAsString(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserFindResponseMessage handle() {
        ArrayList<UserDTO> userDTOS = new UserDAO().searchByUsername("");
        return new UserFindResponseMessage(
                (ArrayList<String>) userDTOS.stream().map(UserDTO::getUsername).collect(Collectors.toList()));
    }
}
