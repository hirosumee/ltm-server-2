package com.chatting.message;

import java.util.ArrayList;

public class MessageListResponse {
    private String event = "message.list";
    private int room = -1;
    private ArrayList<MessageData> messages = new ArrayList<>();

    public MessageListResponse(int room, ArrayList<MessageData> messages) {
        this.room = room;
        this.messages = messages;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public ArrayList<MessageData> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageData> messages) {
        this.messages = messages;
    }
}
