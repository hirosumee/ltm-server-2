package com.chatting.dtos;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageTextDTO extends MessageDTO {
    public String content;

    public static MessageTextDTO fromResultSet(ResultSet rs) throws SQLException {
        MessageTextDTO fl = new MessageTextDTO();
        fl.id = rs.getInt(1);
        fl.id_room = rs.getInt(2);
        fl.id_join = rs.getInt(3);
        fl.content = rs.getString(4).trim();
        fl.time = rs.getDate(5);
        fl.username = rs.getString(6).trim();
        return fl;
    }
}
