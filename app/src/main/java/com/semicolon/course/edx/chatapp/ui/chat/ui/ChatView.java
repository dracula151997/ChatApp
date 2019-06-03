package com.semicolon.course.edx.chatapp.ui.chat.ui;

import com.semicolon.course.edx.chatapp.model.ChatMessage;

public interface ChatView {
    void onMessageRecieved(ChatMessage msg);
    void sendMessage();
}
