package com.chatting.message;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupKickMessage extends Message{
    public  static final String type = "group.kick";
    public final String event = "group.kick";
    public String username;
    public int room;
}
