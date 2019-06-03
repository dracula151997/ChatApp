package com.semicolon.course.edx.chatapp.ui.main.interactor;

import com.semicolon.course.edx.chatapp.ui.main.repository.ContactListRepositoryImpl;
import com.semicolon.course.edx.chatapp.ui.main.repository.IContactListRepository;

public class ContactListInteractorImpl implements IContactListInteractor {
    private IContactListRepository contactListRepository;

    public ContactListInteractorImpl() {
        contactListRepository = new ContactListRepositoryImpl();
    }

    @Override
    public void subscribeForContactsEvent() {
        contactListRepository.subscreibeForContactsListUpdated();


    }

    @Override
    public void unSubscribeForContactsEvent() {
        contactListRepository.unSubscribeForContactsListUpdated();

    }

    @Override
    public void destroyContactListListener() {
        contactListRepository.destroyContactListListener();

    }

    @Override
    public void removeContact(String email) {
        contactListRepository.removeContact(email);

    }
}
