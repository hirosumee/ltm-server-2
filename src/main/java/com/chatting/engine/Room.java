package com.chatting.engine;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private String roomName;
    private Set<Session> sessions;

    Room(String roomName) {
        this.roomName = roomName;
        sessions = new HashSet<>();
    }

    public String getRoomName() {
        return roomName;
    }


    public void send(String message) {
        this.sessions.forEach(i -> {
            i.send(message);
        });
    }
    public void join(Session con) {
        this.sessions.add(con);
        con.join(this);
    }
    public void leave(Session con) {
        this.sessions.remove(con);
        con.leave(this);
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
