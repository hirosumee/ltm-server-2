package com.chatting.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomListResponseMessage extends Message {
    private String event = "room.list";
    private String status;
    private String message;

    private ArrayList<RoomMessage> data = new ArrayList<>();

    public RoomListResponseMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public RoomListResponseMessage(ArrayList<RoomMessage> data) {
        this.data = data;
        this.status = "success";
    }

    public String getEvent() {
        return event;
    }

    public ArrayList<RoomMessage> getData() {
        return data;
    }

    public void setData(ArrayList<RoomMessage> data) {
        this.data = data;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
