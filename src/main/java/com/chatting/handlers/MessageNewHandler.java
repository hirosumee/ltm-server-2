package com.chatting.handlers;

import com.chatting.daos.MessageTextDAO;
import com.chatting.dtos.JoinDTO;
import com.chatting.dtos.UserDTO;
import com.chatting.engine.Connection;
import com.chatting.engine.Room;
import com.chatting.engine.Server;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.message.MessageListMessage;
import com.chatting.message.MessageListResponse;
import com.chatting.message.MessageNew;
import com.chatting.message.MessageNewResponse;
import com.chatting.utils.RoomUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MessageNewHandler implements Handlable {
    @Override
    public void execute(Server server, Connection connection, String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            MessageNew message = mapper.readValue(msg, MessageNew.class);
            String username = connection.getSession().getUsername();
            MessageNewResponse res = handle(username, message.getRoom(), message.getContent());
            if(res != null) {
                Room room = server.getRoom(String.valueOf(message.getRoom()));
                System.out.printf(room.getRoomName());
                room.send(mapper.writeValueAsString(res));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private MessageNewResponse handle(String username, int room, String content) {
        JoinDTO join = RoomUtils.getFirstJoin(username, room);
        if (join != null) {
            int id = (int)new MessageTextDAO().create(join.getId(), join.getId_room(), content, username);
            return new MessageNewResponse(id, room, content, username);
        }
        return null;
    }
}
