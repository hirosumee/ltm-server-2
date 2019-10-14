package com.chatting.engine;

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
        server.removeConnection(this);
    }

    void onError(Exception e) {
        server.removeConnection(this);
    }

    void onSignOut() {
        webSocket.close();
        server.removeConnection(this);
    }

    void onSignIn(Session session) {
        this.session = session;
        this.server.addSession(this);
    }
    public void send(String message) {
        webSocket.send(message);
    }
    WebSocket getWebSocket() {
        return webSocket;
    }

    Session getSession() {
        return session;
    }
}
