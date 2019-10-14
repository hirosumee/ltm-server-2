package com.chatting.engine;

import com.chatting.engine.interfaces.User;

public class Session {
    private User user;
    public String getUsername() {
       return user.getUserName();
    }
}
