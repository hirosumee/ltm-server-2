package com.chatting.message;

import com.chatting.dtos.MessageDTO;
import com.chatting.dtos.MessageTextDTO;

import java.util.Date;

public class MessageData extends Message {
    public int id;
    public int id_join;
    public Date time;
    public int id_room;
    public String username;
    public String content;
    public static MessageData fromDTO(MessageTextDTO dto) {
        MessageData m = new MessageData();
        m.id = dto.id;
        m.id_join = dto.id_join;
        m.time = dto.time;
        m.id_room = dto.id_room;
        m.username = dto.username;
        m.content = dto.content;
        return m ;
    }
}
