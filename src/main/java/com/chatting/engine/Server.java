package com.chatting.engine;

import com.chatting.engine.interfaces.Handlable;
import com.chatting.engine.interfaces.Middleware;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Server extends WebSocketServer {
    private final static Logger logger = LogManager.getLogger(Server.class);
    //<Type, Handler>
    private Map<String, Handlable> handlers;
    //<Username, Session>
    private Map<String, Session> sessions;
    //    <socket, con> affect for mapping message
    private Map<WebSocket, Connection> cons;
    // <name, room>
    private Map<String, Room> rooms;
    //
    private Set<Middleware> middlewares;

    public Server(int port) {
        super(new InetSocketAddress(port));
        initProperties();
        logger.info("Server is listening");
    }

    private void initProperties() {
        handlers = new HashMap<>();
        sessions = new HashMap<>();
        cons = new HashMap<>();
        rooms = new HashMap<>();
        middlewares = new HashSet<>();
    }


    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        logger.info("Connection established from: " + webSocket.getRemoteSocketAddress().getHostString());
        cons.put(webSocket, new Connection(this, webSocket));
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        if (existConnection(webSocket)) {
            getConnection(webSocket).onClose();
            this.cons.remove(webSocket);
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        if (existConnection(webSocket)) {
            getConnection(webSocket).onError(e);
            webSocket.close();
        }
        e.printStackTrace();
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            TempMessage _msg = mapper.readValue(message, TempMessage.class);
            String type = _msg.type;
            if (existHandler(type) && existConnection(webSocket)) {
                Connection connection = getConnection(webSocket);
                if (!this.shouldBeContinue(connection, _msg)) {
                    System.out.println("message do not pass middlewares");
                    return;
                }
                getHandler(type).execute(this, connection, message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //another func
    private synchronized Handlable getHandler(String type) {
        return handlers.get(type);
    }

    private synchronized Connection getConnection(WebSocket web) {
        return cons.get(web);
    }

    private synchronized boolean existHandler(String type) {
        return handlers.containsKey(type);
    }

    private synchronized boolean existConnection(WebSocket con) {
        return cons.containsKey(con);
    }

    synchronized void removeConnection(Connection connection) {
        this.cons.remove(connection.getWebSocket());
    }

    public synchronized void registerHandler(String type, Handlable handler) {
        this.handlers.put(type, handler);
    }

    public synchronized void unRegisterHandler(String type) {
        this.handlers.remove(type);
    }

    synchronized void addSession(Session session) {
        String username = session.getUsername();
        if (!this.sessions.containsKey(username)) {
            this.sessions.put(username, session);
        }
    }

    public synchronized Session getSession(String username) {
        return this.sessions.get(username);
    }

    synchronized void removeSession(String username) {
        this.sessions.remove(username);
    }

    private synchronized Room addRoom(Room room) {
        if (!rooms.containsKey(room.getRoomName())) {
            rooms.put(room.getRoomName(), room);
            return room;
        }
        return rooms.get(room.getRoomName());
    }

    public synchronized Room addRoom(String name) {
        return addRoom(new Room(name));
    }

    synchronized void removeRoom(String roomName) {
        rooms.remove(roomName);
    }

    public synchronized Room getRoom(String roomName) {
        if (!rooms.containsKey(roomName)) {
            rooms.put(roomName, new Room(roomName));
        }
        return rooms.get(roomName);
    }

    public synchronized String getSessionStatus(String username) {
        boolean f = this.sessions.containsKey(username);
        if (!f) return SessionStatus.offline;
        return this.sessions.get(username).getStatus();
    }

    public synchronized void registerMiddleware(Middleware middleware) {
        this.middlewares.add(middleware);
    }

    public synchronized void unRegisterMiddleware(String type) {
        this.middlewares.remove(type);
    }

    private synchronized boolean shouldBeContinue(Connection connection, TempMessage message) {
        for (Middleware middleware : this.middlewares) {
            if (!middleware.execute(connection, message)) return false;
        }
        return true;
    }
}
