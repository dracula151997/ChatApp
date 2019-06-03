package com.semicolon.course.edx.chatapp.ui.chat.presenter;

import com.semicolon.course.edx.chatapp.lib.EventBus;
import com.semicolon.course.edx.chatapp.lib.GreenRobotEventBus;
import com.semicolon.course.edx.chatapp.model.ChatMessage;
import com.semicolon.course.edx.chatapp.model.User;
import com.semicolon.course.edx.chatapp.ui.chat.event.ChatEvent;
import com.semicolon.course.edx.chatapp.ui.chat.interactor.ChatInteractor;
import com.semicolon.course.edx.chatapp.ui.chat.interactor.ChatInteractorImpl;
import com.semicolon.course.edx.chatapp.ui.chat.interactor.ChatSessionInteractor;
import com.semicolon.course.edx.chatapp.ui.chat.interactor.ChatSessionInteractorImpl;
import com.semicolon.course.edx.chatapp.ui.chat.ui.ChatView;

import org.greenrobot.eventbus.Subscribe;

public class ChatPresenterImpl implements ChatPresenter {
    private EventBus eventBus;
    private ChatView chatView;
    private ChatInteractor interactor;
    private ChatSessionInteractor sessionInteractor;

    public ChatPresenterImpl(ChatView chatView) {
        this.chatView = chatView;
        this.eventBus = new GreenRobotEventBus();
        this.interactor = new ChatInteractorImpl();
        this.sessionInteractor = new ChatSessionInteractorImpl();
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        if (chatView != null){
            ChatMessage message = event.getChatMessage();
            chatView.onMessageRecieved(message);
        }



    }

    @Override
    public void sendMessage(String msg) {
        interactor.sendMessage(msg);

    }

    @Override
    public void setReceiver(String receiver) {
        interactor.setReceiver(receiver);

    }

    @Override
    public void onPause() {
        sessionInteractor.changeUserConnectionStatus(User.OFFLINE);
        interactor.unSupscribeForChatUpdates();


    }

    @Override
    public void onResume() {
        sessionInteractor.changeUserConnectionStatus(User.ONLINE);
        interactor.subscribeForChatUpdates();

    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        sessionInteractor.changeUserConnectionStatus(User.OFFLINE);
        chatView = null;

    }

    @Override
    public void onCreate() {
        eventBus.register(this);

    }
}
