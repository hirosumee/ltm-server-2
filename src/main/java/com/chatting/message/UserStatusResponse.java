package com.chatting.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStatusResponse extends Message {
    public String event = "user.status";
    public String username;
    public String status;

    public UserStatusResponse(String username, String status) {
        this.username = username;
        this.status = status;
    }
}
