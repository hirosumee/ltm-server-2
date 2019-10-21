package com.chatting.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFindMessage extends Message{
    public static final String type = "user.find";
}
