package com.chatting.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageListMessage extends Message {
    public static final String type = "message.list";
    private int room = -1;


    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

}
