package com.chatting.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JoinListMessage extends Message{
    public static final String type = "join.list";
    public int room;
}
