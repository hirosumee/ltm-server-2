package com.chatting.engine;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class User implements Serializable {
    private String id;
    private String username;
    public User() {
    }

    public User(String name) {
        this.username = name;
        id = UUID.randomUUID().toString();
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if(null == o || o.getClass() != this.getClass()) return false;
        User other = (User) o;
        return Objects.equals(this.id, other.id) && Objects.equals(this.username, other.username);
    }
}
