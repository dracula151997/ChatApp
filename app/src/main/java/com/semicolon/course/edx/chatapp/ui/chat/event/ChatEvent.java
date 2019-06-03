package com.semicolon.course.edx.chatapp.ui.chat.event;

import com.semicolon.course.edx.chatapp.model.ChatMessage;

public class ChatEvent {
    private ChatMessage chatMessage;

    public ChatEvent(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }
}
