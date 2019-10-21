package com.chatting.engine;

import com.chatting.dtos.UserDTO;
import com.chatting.engine.exceptions.SessionNotSetException;
import org.java_websocket.WebSocket;


public class Connection {
    private WebSocket webSocket;
    private Server server;
    private Session session;

    Connection(Server server, WebSocket webSocket) {
        this.webSocket = webSocket;
        this.server = server;
    }

    void onClose() {
        if (session != null) {
            // remove this connection from session
            session.removeConnection(this);
            System.out.println(session.getUsername() + " disconnected");
        }
    }


    void onError(Exception e) {
        if (session != null) {
            session.removeConnection(this);
        }
    }

    void onSignOut() {
        webSocket.close();
    }

    public void onSignIn(UserDTO user) {
        Session session = findExistSession(user.getUsername());
        if (session == null) {
            session = new Session(user);
            server.addSession(session);
        }
        this.session = session;
        System.out.println(session.getUsername() + " has logged in!");
        session.addConnection(this);
    }

    private Session findExistSession(String username) {
        return this.server.getSession(username);
    }

    public void send(String message) {
        if (webSocket.isClosed()) return;
        webSocket.send(message);
    }

    WebSocket getWebSocket() {
        return webSocket;
    }

    public Session getSession() {
        return session;
    }

    public void joinRoom(Room room) throws SessionNotSetException {
        if (session != null) {
            session.join(room);
        } else {
            throw new SessionNotSetException();
        }
    }

    public void leaveRoom(Room room) throws SessionNotSetException {
        if (session != null) {
            session.leave(room);
        } else {
            throw new SessionNotSetException();
        }
    }
}
