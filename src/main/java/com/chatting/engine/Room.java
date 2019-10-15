package com.chatting.engine;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private String roomName;
    private Set<Connection> connections;

    public Room(String roomName) {
        this.roomName = roomName;
        connections = new HashSet<>();
    }

    public String getRoomName() {
        return roomName;
    }

    public Set<Connection> getConnections() {
        return connections;
    }

    public void send(String message) {
        this.connections.forEach(i -> {
            i.send(message);
        });
    }
    public void join(Connection con) {
        this.connections.add(con);
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
