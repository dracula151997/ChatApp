package com.semicolon.course.edx.chatapp.ui.addcontact.presenter;

import com.semicolon.course.edx.chatapp.lib.EventBus;
import com.semicolon.course.edx.chatapp.lib.GreenRobotEventBus;
import com.semicolon.course.edx.chatapp.ui.addcontact.event.AddContactEvent;
import com.semicolon.course.edx.chatapp.ui.addcontact.interactor.AddContactInteractor;
import com.semicolon.course.edx.chatapp.ui.addcontact.interactor.AddContactnteractorImpl;
import com.semicolon.course.edx.chatapp.ui.addcontact.ui.AddContactView;

import org.greenrobot.eventbus.Subscribe;

public class AddContactPresenterImpl implements AddContactPresenter {
    private AddContactInteractor interactor;
    private AddContactView addContactView;
    private EventBus eventBus;

    public AddContactPresenterImpl(AddContactView contactView) {
        interactor = new AddContactnteractorImpl();
        this.addContactView = contactView;
        this.eventBus = GreenRobotEventBus.getInstance();

    }

    @Override
    public void addContact(String email) {
        interactor.addContact(email);
        addContactView.showProgress();

    }

    @Override
    public void onDestroy() {
        addContactView = null;
        eventBus.unregister(this);

    }

    @Override
    public void onShow() {
        eventBus.register(this);

    }

    @Override
    @Subscribe
    public void onEventMainThread(AddContactEvent event) {
        if (addContactView != null) {
            addContactView.hideProgress();
            addContactView.showInput();

            if (event.isError()) {
                addContactView.contactNotAdded();
            } else {
                addContactView.contactAdded();
            }
        }


    }
}
