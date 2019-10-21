package com.chatting.message;

import java.util.Date;

public class MessageNewResponse extends Message{
    private String event = "message.new";
    private int id = -1;
    private int id_room = -1;
    private String content;
    private String username;
    private Date time = new Date();

    public MessageNewResponse(int id, int room, String content, String sender) {
        this.id = id;
        this.id_room = room;
        this.content = content;
        this.username= sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId_room() {
        return id_room;
    }

    public void setId_room(int id_room) {
        this.id_room = id_room;
    }
}
