package com.chatting.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupCreateMessage extends Message{
    public static String type = "group.create";
    public String name;
}
