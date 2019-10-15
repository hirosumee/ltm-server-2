package com.chatting.handlers;

import com.chatting.daos.UserDAO;
import com.chatting.dtos.UserDTO;
import com.chatting.engine.Connection;
import com.chatting.engine.Server;
import com.chatting.engine.Session;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.exceptions.RecordNotFoundException;
import com.chatting.message.CommonResponseMessage;
import com.chatting.message.LoginMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginHandler implements Handlable {
    private Connection connection;
    @Override
    public void execute(Server server, Connection connection, String msg) {
        this.connection = connection;
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoginMessage message = mapper.readValue(msg, LoginMessage.class);
            CommonResponseMessage res = handle(message.getUsername(), message.getPassword());
            connection.send(mapper.writeValueAsString(res));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private CommonResponseMessage handle(String username, String password) {
        try {
            UserDTO userDTO = new UserDAO().findByUsername(username);
            boolean isValid = userDTO.comparePassword(password);
            if(!isValid) {
                return new CommonResponseMessage("login", "failed", "Mật khẩu sai");
            }
            connection.onSignIn(new Session(new UserDTO(username, password)));
            return new CommonResponseMessage("login", "successful", username);
        } catch (RecordNotFoundException | NoSuchAlgorithmException e) {
            return new CommonResponseMessage("login", "failed", "User không tồn tại");
        }
    }
}
