package com.semicolon.course.edx.chatapp.ui.main.presenter;

import com.semicolon.course.edx.chatapp.lib.EventBus;
import com.semicolon.course.edx.chatapp.lib.GreenRobotEventBus;
import com.semicolon.course.edx.chatapp.model.User;
import com.semicolon.course.edx.chatapp.ui.main.event.ContactListEvent;
import com.semicolon.course.edx.chatapp.ui.main.interactor.ContactListInteractorImpl;
import com.semicolon.course.edx.chatapp.ui.main.interactor.ContactListSessionInteractorImpl;
import com.semicolon.course.edx.chatapp.ui.main.interactor.IContactListInteractor;
import com.semicolon.course.edx.chatapp.ui.main.interactor.IContactListSessionInteractor;
import com.semicolon.course.edx.chatapp.ui.main.ui.IContactListView;

import org.greenrobot.eventbus.Subscribe;

public class ContactListPresenterImpl implements IContactListPresenter {
    private IContactListSessionInteractor contactListSessionInteractor;
    private IContactListInteractor contactListInteractor;
    private IContactListView contactListView;
    private EventBus eventBus;


    public ContactListPresenterImpl(IContactListView contactListView) {
        this.contactListView = contactListView;
        contactListInteractor = new ContactListInteractorImpl();
        contactListSessionInteractor = new ContactListSessionInteractorImpl();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void signOff() {
        contactListSessionInteractor.signOff();
        contactListInteractor.destroyContactListListener();
        contactListInteractor.unSubscribeForContactsEvent();
        contactListSessionInteractor.changeUserConnectionStatus(User.OFFLINE);
    }

    @Override
    public String getCurrentEmail() {
        return contactListSessionInteractor.getCurrentEmail();
    }

    @Override
    public void removeContact(String email) {
        contactListInteractor.removeContact(email);


    }

    @Override
    @Subscribe
    public void onEventMainThread(ContactListEvent event) {
        User user = event.getUser();
        switch (event.getType()) {
            case ContactListEvent.onContactAdded:
                onContactAdded(user);
                break;
            case ContactListEvent.onContactRemoved:
                onContactRemoved(user);
                break;
            case ContactListEvent.onContactChanged:
                onContactChanged(user);
                break;

        }

    }

    private void onContactChanged(User user) {
        if (contactListView != null) {
            contactListView.onUserChanged(user);
        }
    }

    private void onContactRemoved(User user) {
        if (contactListView != null) {
            contactListView.onUserRemoved(user);
        }
    }

    private void onContactAdded(User user) {
        if (contactListView != null) {
            contactListView.onUserAdded(user);
        }
    }

    @Override
    public void onPause() {
        contactListSessionInteractor.changeUserConnectionStatus(User.OFFLINE);
        contactListInteractor.unSubscribeForContactsEvent();

    }

    @Override
    public void onResume() {
        contactListSessionInteractor.changeUserConnectionStatus(User.ONLINE);
        contactListInteractor.subscribeForContactsEvent();

    }

    @Override
    public void onCreate() {
        eventBus.register(this);

    }

    @Override
    public void onDestroy() {
        contactListSessionInteractor.changeUserConnectionStatus(User.OFFLINE);
        contactListInteractor.destroyContactListListener();
        contactListView = null;

    }
}
