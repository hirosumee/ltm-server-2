package com.chatting;

import com.chatting.engine.Server;
import com.chatting.handlers.LoginHandler;
import com.chatting.handlers.RegisterHandler;
import com.chatting.handlers.RoomListHandler;
import com.chatting.message.LoginMessage;
import com.chatting.message.RegisterMessage;
import com.chatting.message.RoomListMessage;
import com.chatting.vendor.DbConnection;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try {
            DbConnection.getInstance();
            Server server = new Server(8080);
            server.registerHandler(RegisterMessage.type, new RegisterHandler());
            server.registerHandler(LoginMessage.type, new LoginHandler());
            server.registerHandler(RoomListMessage.type, new RoomListHandler());
            server.start();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
