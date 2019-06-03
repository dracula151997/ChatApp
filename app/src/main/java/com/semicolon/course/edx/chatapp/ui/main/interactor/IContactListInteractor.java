package com.semicolon.course.edx.chatapp.ui.main.interactor;

public interface IContactListInteractor {
    void subscribeForContactsEvent();
    void unSubscribeForContactsEvent();
    void destroyContactListListener();
    void removeContact(String email);
}
