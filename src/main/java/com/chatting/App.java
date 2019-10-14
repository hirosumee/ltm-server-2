package com.chatting;

import com.chatting.engine.Server;
import com.chatting.handlers.LoginHandler;
import com.chatting.handlers.RegisterHandler;
import com.chatting.message.LoginMessage;
import com.chatting.message.RegisterMessage;

public class App {
    public static void main(String[] args) {
        Server server = new Server(8080);
        server.registerHandler(RegisterMessage.type, new RegisterHandler());
        server.registerHandler(LoginMessage.type, new LoginHandler());
        server.start();
    }
}
