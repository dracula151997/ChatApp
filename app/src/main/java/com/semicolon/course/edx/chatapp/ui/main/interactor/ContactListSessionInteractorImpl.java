package com.semicolon.course.edx.chatapp.ui.main.interactor;

import com.semicolon.course.edx.chatapp.ui.main.repository.ContactListRepositoryImpl;
import com.semicolon.course.edx.chatapp.ui.main.repository.IContactListRepository;

public class ContactListSessionInteractorImpl implements IContactListSessionInteractor {
    private IContactListRepository repository;

    public ContactListSessionInteractorImpl() {
        repository = new ContactListRepositoryImpl();

    }

    @Override
    public void signOff() {
        repository.signOff();

    }

    @Override
    public String getCurrentEmail() {
        return repository.getCurrentEmail();
    }

    @Override
    public void changeUserConnectionStatus(boolean online) {
        repository.changeUserConnectionStatus(online);

    }

    @Override
    public void removeContact(String email) {

    }
}
