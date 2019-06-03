package com.semicolon.course.edx.chatapp.model;

import com.google.firebase.database.Exclude;

public class ChatMessage {
    private String message;
    private String sender;
    @Exclude
    boolean sentByMe;

    public ChatMessage() {
    }

    public ChatMessage(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(boolean sentByMe) {
        this.sentByMe = sentByMe;
    }
}
