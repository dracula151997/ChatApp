package com.semicolon.course.edx.chatapp.ui.main.presenter;

import com.semicolon.course.edx.chatapp.ui.main.event.ContactListEvent;

public interface IContactListPresenter {
    void signOff();

    String getCurrentEmail();

    void removeContact(String email);

    void onEventMainThread(ContactListEvent event);

    void onPause();

    void onResume();

    void onCreate();

    void onDestroy();
}
