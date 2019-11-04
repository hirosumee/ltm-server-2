package com.chatting.engine;

import com.chatting.engine.interfaces.User;

import java.util.HashSet;
import java.util.Set;

public class Session {
    private User user;
    private Set<Connection> connections = new HashSet<>();
    private Set<Room> rooms = new HashSet<>();
    private String status = SessionStatus.idle;
    private boolean persistStatus = false;

    Session(User user) {
        this.user = user;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public User getUser() {
        return user;
    }

    public void send(String message) {
        connections.forEach(con -> con.send(message));
    }

    synchronized void addConnection(Connection con) {
        this.connections.add(con);
        if (!status.equals(SessionStatus.online)) {
            setStatus(SessionStatus.online);
        }
    }

    synchronized void removeConnection(Connection con) {
        this.connections.remove(con);
        if (this.connections.size() == 0) {
            waitReconnect();
        }
    }

    private synchronized void waitReconnect() {
        this.setStatus(SessionStatus.idle);
        Thread thread = new IdleThread(this);
        thread.start();
    }

    synchronized void join(Room room) {
        this.rooms.add(room);
    }

    synchronized void leave(Room room) {
        this.rooms.remove(room);
    }

    synchronized void onClose() {
        for (Room room : rooms) {
            room.leave(this);
        }
    }

    synchronized public String getStatus() {
        return status;
    }

    synchronized public void setStatus(String status) {
        if (!isPersistStatus()) {
            this.status = status;
        }
    }

    synchronized private boolean isPersistStatus() {
        return this.persistStatus;
    }

    synchronized public void setPersistStatus(String status) {
        persistStatus = !status.equals(SessionStatus.online);
        this.status = status;
    }

    synchronized int getConnections() {
        return this.connections.size();
    }
}
