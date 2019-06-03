package com.semicolon.course.edx.chatapp.ui.chat.repository;

public interface ChatRepository {
    void sendMessage(String msg);

    void setReceiver(String receiver);

    void subscribeForChatUpdates();

    void unSupscribeForChatUpdates();

    void destroyChatListener();

    void changeUserConnectionStatus(boolean online);


}
