package com.semicolon.course.edx.chatapp.ui.main.repository;

public interface IContactListRepository {
    void signOff();

    String getCurrentEmail();

    void removeContact(String email);

    void destroyContactListListener();

    void subscreibeForContactsListUpdated();

    void unSubscribeForContactsListUpdated();

    void changeUserConnectionStatus(boolean online);
}
