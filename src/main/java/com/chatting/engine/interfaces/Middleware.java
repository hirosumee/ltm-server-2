package com.chatting.engine.interfaces;

import com.chatting.engine.Connection;
import com.chatting.engine.TempMessage;

public interface Middleware {
    boolean execute(Connection connection, TempMessage message);
}
