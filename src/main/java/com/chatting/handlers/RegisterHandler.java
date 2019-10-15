package com.chatting.handlers;

import com.chatting.daos.RoomDAO;
import com.chatting.daos.UserDAO;
import com.chatting.dtos.UserDTO;
import com.chatting.engine.Connection;
import com.chatting.engine.Server;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.exceptions.RecordNotFoundException;
import com.chatting.message.CommonResponseMessage;
import com.chatting.message.Message;
import com.chatting.message.RegisterMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class RegisterHandler implements Handlable {
    @Override
    public void execute(Server server, Connection connection, String msg) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            RegisterMessage message = mapper.readValue(msg, RegisterMessage.class);
            CommonResponseMessage response = handle(message.getUsername(), message.getPassword());
            connection.send(mapper.writeValueAsString(response));
            System.out.println(message.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CommonResponseMessage handle(String username, String password) {
        UserDAO userDAO = new UserDAO();
        RoomDAO roomDAO = new RoomDAO();
        try {
            userDAO.findByUsername(username);
            System.out.println("user is exist");
            return new CommonResponseMessage("register", "error", "Tài khoản đã tồn tại");
        } catch (RecordNotFoundException e) {
            try {
                UserDTO userDTO = new UserDTO(username, password);
                userDAO.create(userDTO);
                roomDAO.createSelfRoom(userDTO.getUsername());
                System.out.println(userDTO);

                return new CommonResponseMessage("register", "success", String.format("Chào mừng %s", username));
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
                return new CommonResponseMessage("register", "error", "Có lỗi xảy ra thử lại sau");
            }
        }
    }
}
