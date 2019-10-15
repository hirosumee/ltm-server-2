package com.chatting.engine;

import com.chatting.engine.interfaces.Handlable;
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
    private Map<WebSocket, Connection> cons;
    private Map<Session, Set<Connection>> sessionSetMap;
    private Map<String, Room> rooms;

    public Server(int port) {
        super(new InetSocketAddress(port));
        initProperties();
        logger.info("Server is listening");
    }

    private void initProperties() {
        handlers = new HashMap<>();
        sessions = new HashMap<>();
        cons = new HashMap<>();
        sessionSetMap = new HashMap<>();
        rooms = new HashMap<>();
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
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            TempMessage _msg = mapper.readValue(message, TempMessage.class);
            String type = _msg.type;
            if (existHandler(type) && existConnection(webSocket)) {
                Connection connection = getConnection(webSocket);
                getHandler(type).execute(this, connection, message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        if (existConnection(webSocket)) {
            getConnection(webSocket).onError(e);
        }
    }


    //another func
    private Handlable getHandler(String type) {
        return handlers.get(type);
    }

    private Connection getConnection(WebSocket web) {
        return cons.get(web);
    }

    private boolean existHandler(String type) {
        return handlers.containsKey(type);
    }

    private boolean existConnection(WebSocket con) {
        return cons.containsKey(con);
    }

    void removeConnection(Connection connection) {
        this.cons.remove(connection.getWebSocket());
        removeConnectionFrom(connection);
    }

    private void removeConnectionFrom(Connection connection) {
        sessionSetMap.forEach((k, v) -> {
            v.remove(connection);
        });
    }

    private void addConnectionTo(Session session, Connection connection) {
        if (sessionSetMap.containsKey(session)) {
            sessionSetMap.put(session, new HashSet<>());
        }
        sessionSetMap.get(session).add(connection);
    }

    //
    public void registerHandler(String type, Handlable handler) {
        this.handlers.put(type, handler);
    }

    public void unRegisterHandler(String type) {
        this.handlers.remove(type);
    }

    void addSession(Connection connection) {
        Session session = connection.getSession();
        String username = session.getUsername();
        if (!this.sessions.containsKey(username)) {
            this.sessions.put(username, session);
            addConnectionTo(session, connection);
        }
    }

    public Room addRoom(Room room) {
        if (!rooms.containsKey(room.getRoomName())) {
            rooms.put(room.getRoomName(), room);
            return room;
        }
        return rooms.get(room.getRoomName());
    }
    public Room addRoom(String name) {
       return addRoom(new Room(name));
    }
    void removeRoom(String roomName) {
        rooms.remove(roomName);
    }
    public Room getRoom(String roomName) {
        return rooms.get(roomName);
    }

    void removeSession(String username) {
        this.sessions.remove(username);
    }

    private boolean isOnline(Session session) {
        if (!sessionSetMap.containsKey(session)) return false;
        Set<Connection> set = sessionSetMap.get(session);
        return set.size() > 0;
    }

    boolean isOnline(String username) {
        Session session = null;
        for (Session ss : sessionSetMap.keySet()) {
            if (ss.getUsername().equals(username)) {
                session = ss;
            }
        }
        if (session == null) return false;
        return isOnline(session);
    }

}
