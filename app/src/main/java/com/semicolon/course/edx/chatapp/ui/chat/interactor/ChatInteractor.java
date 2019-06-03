package com.semicolon.course.edx.chatapp.ui.chat.interactor;

public interface ChatInteractor {
    void sendMessage(String msg);

    void setReceiver(String receiver);

    void subscribeForChatUpdates();

    void unSupscribeForChatUpdates();

    void destroyChatListener();

}
