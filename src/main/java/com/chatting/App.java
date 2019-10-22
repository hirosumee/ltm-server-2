package com.chatting;

import com.chatting.engine.Server;
import com.chatting.handlers.*;
import com.chatting.message.*;
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
            server.registerHandler(MessageListMessage.type, new MessageListHandler());
            server.registerHandler(MessageNew.type, new MessageNewHandler());
            server.registerHandler(UserFindMessage.type, new UserFindHandler());
            server.registerHandler(RoomCreateMessage.type, new RoomCreateHandler());
            server.registerHandler(GroupCreateMessage.type, new GroupCreateHandler());
            server.registerHandler(JoinListMessage.type, new JoinListHandler());
            server.registerHandler(UserStatusMessage.type, new UserStatusHandler());
            server.start();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
