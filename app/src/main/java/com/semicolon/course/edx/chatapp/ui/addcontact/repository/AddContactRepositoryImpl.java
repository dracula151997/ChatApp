package com.semicolon.course.edx.chatapp.ui.addcontact.repository;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.semicolon.course.edx.chatapp.lib.EventBus;
import com.semicolon.course.edx.chatapp.lib.FirebaseHelper;
import com.semicolon.course.edx.chatapp.lib.GreenRobotEventBus;
import com.semicolon.course.edx.chatapp.model.User;
import com.semicolon.course.edx.chatapp.ui.addcontact.event.AddContactEvent;

public class AddContactRepositoryImpl implements AddContactRepository {
    @Override
    public void addContact(String email) {
        String key = email.replace(".", "_");
        FirebaseHelper helper = FirebaseHelper.getInstance();
        final DatabaseReference userReference = helper.getUserReference(email);
        AddContactEvent event = new AddContactEvent();
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    boolean online = user.isOnline();
                    FirebaseHelper firebaseHelper = FirebaseHelper.getInstance();
                    DatabaseReference userContactsReference = firebaseHelper.getMyContactsReference();
                    userContactsReference.child(key).setValue(online);

                    String currentUserEmailKey = firebaseHelper.getAuthUserEmail();
                    currentUserEmailKey = currentUserEmailKey.replace(".", "_");
                    DatabaseReference contactsReference = firebaseHelper.getContactsReference(email);
                    contactsReference.child(currentUserEmailKey).setValue(true);

                } else {
                    event.setError(true);

                }

                EventBus eventBus = GreenRobotEventBus.getInstance();
                eventBus.post(event);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
