package com.chatting.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JoinListMessageResponse extends Message {
    public String event = "join.list";
    public ArrayList<String> usernames = new ArrayList<>();

    public JoinListMessageResponse(ArrayList<String> usernames) {
        this.usernames = usernames;
    }
}
