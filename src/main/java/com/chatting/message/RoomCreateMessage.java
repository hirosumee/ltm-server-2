package com.chatting.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomCreateMessage extends Message {
    public static String type = "room.create";
    public String username;
    public String name;

}
