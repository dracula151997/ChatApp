package com.semicolon.course.edx.chatapp.ui.main.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.semicolon.course.edx.chatapp.lib.EventBus;
import com.semicolon.course.edx.chatapp.lib.FirebaseHelper;
import com.semicolon.course.edx.chatapp.lib.GreenRobotEventBus;
import com.semicolon.course.edx.chatapp.model.User;
import com.semicolon.course.edx.chatapp.ui.main.event.ContactListEvent;

public class ContactListRepositoryImpl implements IContactListRepository {
    private FirebaseHelper firebaseHelper;

    private ChildEventListener contactListEventListener;

    public ContactListRepositoryImpl() {
        firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void signOff() {
        firebaseHelper.signOff();

    }

    @Override
    public String getCurrentEmail() {
        return firebaseHelper.getAuthUserEmail();
    }

    @Override
    public void removeContact(String email) {
        String currentUserEmail = firebaseHelper.getAuthUserEmail();
        firebaseHelper.getOnContactsReference(currentUserEmail, email).removeValue();
        firebaseHelper.getOnContactsReference(email, currentUserEmail).removeValue();


    }

    @Override
    public void destroyContactListListener() {
        contactListEventListener = null;

    }

    @Override
    public void subscreibeForContactsListUpdated() {
        if (contactListEventListener == null) {
            contactListEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String email = dataSnapshot.getKey();
                    email = email.replace("_", ".");
                    boolean online = (Boolean) dataSnapshot.getValue();
                    User user = new User(email, online, null);
                    postEvent(ContactListEvent.onContactAdded, user);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String email = dataSnapshot.getKey();
                    email = email.replace("_", ".");
                    boolean online = ((Boolean) dataSnapshot.getValue()).booleanValue();
                    User user = new User(email, online, null);
                    postEvent(ContactListEvent.onContactChanged, user);

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    String email = dataSnapshot.getKey();
                    email = email.replace("_", ".");
                    boolean online = ((Boolean) dataSnapshot.getValue()).booleanValue();
                    User user = new User(email, online, null);
                    postEvent(ContactListEvent.onContactRemoved, user);

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            };

            firebaseHelper.getMyContactsReference().addChildEventListener(contactListEventListener);
        }

    }

    private void postEvent(int type, User user) {
        ContactListEvent contactListEvent = new ContactListEvent();
        contactListEvent.setType(type);
        contactListEvent.setUser(user);

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(contactListEvent);

    }

    @Override
    public void unSubscribeForContactsListUpdated() {
        if (contactListEventListener != null) {
            firebaseHelper.getMyContactsReference().removeEventListener(contactListEventListener);
        }

    }

    @Override
    public void changeUserConnectionStatus(boolean online) {

    }
}
