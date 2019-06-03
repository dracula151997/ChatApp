package com.semicolon.course.edx.chatapp.ui.addcontact.interactor;

import com.semicolon.course.edx.chatapp.ui.addcontact.repository.AddContactRepository;
import com.semicolon.course.edx.chatapp.ui.addcontact.repository.AddContactRepositoryImpl;

public class AddContactnteractorImpl implements AddContactInteractor {
    private AddContactRepository repository;

    public AddContactnteractorImpl() {
        this.repository = new AddContactRepositoryImpl();
    }

    @Override
    public void addContact(String email) {
        repository.addContact(email);

    }
}
