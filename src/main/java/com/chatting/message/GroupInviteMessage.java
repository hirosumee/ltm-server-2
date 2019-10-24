package com.chatting.message;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupInviteMessage extends Message {
    public static final String type = "group.invite";
    public final String event = "group.invite";
    public String username;
    public int room;
}
