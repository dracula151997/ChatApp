package com.semicolon.course.edx.chatapp.ui.main.interactor;

public interface IContactListSessionInteractor {
    void signOff();

    String getCurrentEmail();

    void changeUserConnectionStatus(boolean online);

    void removeContact(String email);


}
