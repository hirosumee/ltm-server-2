package com.chatting.engine.interfaces;

import com.chatting.engine.Connection;
import com.chatting.engine.Server;


public interface Handlable {
    void execute(Server server, Connection connection, String msg);
}
