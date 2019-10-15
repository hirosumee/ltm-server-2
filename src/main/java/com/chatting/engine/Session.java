package com.chatting.engine;

import com.chatting.engine.interfaces.User;

public class Session {
    private User user;
    public String getUsername() {
       return user.getUsername();
    }
    public User  getUser() {
        return user;
    }

    public Session(User user) {
        this.user = user;
    }

}
