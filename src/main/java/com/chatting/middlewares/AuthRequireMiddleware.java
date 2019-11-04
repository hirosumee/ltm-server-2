package com.chatting.middlewares;

import com.chatting.engine.Connection;
import com.chatting.engine.TempMessage;
import com.chatting.engine.interfaces.Middleware;
import com.chatting.message.LoginMessage;
import com.chatting.message.RegisterMessage;

public class AuthRequireMiddleware implements Middleware {
    @Override
    public boolean execute(Connection connection, TempMessage message) {
        String type = message.type;
        if (type.equals(LoginMessage.type) || type.equals(RegisterMessage.type)) return true;
        return connection.getSession().getUsername() != null;
    }
}
