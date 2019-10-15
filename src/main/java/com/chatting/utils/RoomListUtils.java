package com.chatting.utils;


import com.chatting.dtos.RoomDTO;
import com.chatting.message.RoomListResponseMessage;
import com.chatting.message.RoomMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomListUtils {
    public static RoomListResponseMessage fromRoomList(List<RoomDTO> list) {
        ArrayList<RoomMessage> s = (ArrayList<RoomMessage>) list.stream().map(RoomUtils::fromRoom).collect(Collectors.toList());
        return new RoomListResponseMessage(s);
    }
}
