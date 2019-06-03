package com.semicolon.course.edx.chatapp.ui.chat.interactor;

import com.semicolon.course.edx.chatapp.ui.chat.repository.ChatRepository;
import com.semicolon.course.edx.chatapp.ui.chat.repository.ChatRepositoryImpl;

public class ChatInteractorImpl implements ChatInteractor {
    private ChatRepository repository;

    public ChatInteractorImpl() {
        this.repository = new ChatRepositoryImpl();
    }

    @Override
    public void sendMessage(String msg) {
        repository.sendMessage(msg);

    }

    @Override
    public void setReceiver(String receiver) {
        repository.setReceiver(receiver);

    }

    @Override
    public void subscribeForChatUpdates() {
        repository.subscribeForChatUpdates();

    }

    @Override
    public void unSupscribeForChatUpdates() {
        repository.unSupscribeForChatUpdates();

    }

    @Override
    public void destroyChatListener() {
        repository.destroyChatListener();

    }
}
