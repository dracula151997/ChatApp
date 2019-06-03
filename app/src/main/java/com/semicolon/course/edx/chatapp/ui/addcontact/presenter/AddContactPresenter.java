package com.semicolon.course.edx.chatapp.ui.addcontact.presenter;

import com.semicolon.course.edx.chatapp.ui.addcontact.event.AddContactEvent;

public interface AddContactPresenter {
    void addContact(String email);
    void onDestroy();
    void onShow();
    void onEventMainThread(AddContactEvent event);
}
