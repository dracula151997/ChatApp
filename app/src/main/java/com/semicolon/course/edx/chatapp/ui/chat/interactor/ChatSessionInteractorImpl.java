package com.semicolon.course.edx.chatapp.ui.chat.interactor;

import com.semicolon.course.edx.chatapp.ui.chat.repository.ChatRepository;
import com.semicolon.course.edx.chatapp.ui.chat.repository.ChatRepositoryImpl;

public class ChatSessionInteractorImpl implements ChatSessionInteractor {
    private ChatRepository repository;

    public ChatSessionInteractorImpl() {
        repository = new ChatRepositoryImpl();
    }

    @Override
    public void changeUserConnectionStatus(boolean online) {
        repository.changeUserConnectionStatus(online);

    }
}
