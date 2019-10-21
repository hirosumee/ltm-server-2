package com.chatting.handlers;

import com.chatting.daos.MessageTextDAO;
import com.chatting.dtos.MessageTextDTO;
import com.chatting.engine.Connection;
import com.chatting.engine.Server;
import com.chatting.engine.interfaces.Handlable;
import com.chatting.message.MessageData;
import com.chatting.message.MessageListMessage;
import com.chatting.message.MessageListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageListHandler implements Handlable {
    @Override
    public void execute(Server server, Connection connection, String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            MessageListMessage message = mapper.readValue(msg, MessageListMessage.class);
            MessageListResponse res = handle(message.getRoom());
            connection.send(mapper.writeValueAsString(res));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MessageListResponse handle(int room) {
        List<MessageTextDTO>
                messages =
                new MessageTextDAO().getByRoomId(room).stream().map(i -> (MessageTextDTO) i)
                        .collect(Collectors.toList());
        ArrayList<MessageData> data = (ArrayList<MessageData>) messages.stream().map(MessageData::fromDTO).collect(Collectors.toList());
        return new MessageListResponse(room, data);
    }
}
