package com.chatting.utils;


import com.chatting.daos.JoinDAO;
import com.chatting.dtos.JoinDTO;
import com.chatting.dtos.RoomDTO;
import com.chatting.message.RoomMessage;

import java.util.List;

public class RoomUtils {
    public static RoomMessage fromRoom(RoomDTO roomDTO) {
        RoomMessage roomPacket = new RoomMessage();
        roomPacket.id = roomDTO.getId();
        roomPacket.creator = roomDTO.getCreator();
        roomPacket.groupIP = roomDTO.getGroupIP();
        roomPacket.time = roomDTO.getTime();
        roomPacket.update_time = roomDTO.getUpdate_time();
        roomPacket.type = roomDTO.getType();
        return roomPacket;
    }

    public static boolean isMember(String username, int id_room) {
        return new JoinDAO().findByUsernameAndRoom(username, id_room).size() > 0;
    }

    public static JoinDTO getFirstJoin(String username, int id_room) {
        List<JoinDTO> list = new JoinDAO().findByUsernameAndRoom(username, id_room);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
