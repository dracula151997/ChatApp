package com.semicolon.course.edx.chatapp.ui.chat.presenter;

import com.semicolon.course.edx.chatapp.ui.chat.event.ChatEvent;

public interface ChatPresenter {
    void onEventMainThread(ChatEvent event);

    void sendMessage(String msg);

    void setReceiver(String receiver);

    void onPause();

    void onResume();

    void onDestroy();

    void onCreate();
}
