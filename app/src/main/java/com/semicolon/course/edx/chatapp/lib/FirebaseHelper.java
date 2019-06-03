package com.semicolon.course.edx.chatapp.lib;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.semicolon.course.edx.chatapp.model.User;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    private static final String USER_PATH = "users";
    private static final String CONTACTS_PATH = "contacts";
    private DatabaseReference databaseReference;

    public void changeUserConnectionStatus(boolean online) {
        if (getMyUserReference() != null) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("online", online);
            getMyUserReference().updateChildren(updates);

            notifyContactsOfConnectionChange(online);
        }
    }

    private void notifyContactsOfConnectionChange(boolean online) {
        notifyContactsOfConnectionChange(online, false);
    }

    private void notifyContactsOfConnectionChange(boolean online, boolean signOff) {
        String myEmail = getAuthUserEmail();
        getMyUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String email = child.getKey();
                    DatabaseReference reference = getOnContactsReference(email, myEmail);
                    reference.setValue(online);
                }

                if (signOff)
                    FirebaseAuth.getInstance().signOut();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void signOff() {
        notifyContactsOfConnectionChange(User.OFFLINE, true);
    }

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public String getAuthUserEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;
        if (user != null) {
            email = user.getEmail();
        }

        return email;

    }

    public DatabaseReference getUserReference(String email) {
        DatabaseReference reference = null;
        if (email != null) {
            String emailKey = email.replace(".", "_");
            reference = databaseReference.getRoot().child(USER_PATH).child(emailKey);
        }

        return reference;
    }

    public DatabaseReference getMyUserReference() {
        return getUserReference(getAuthUserEmail());
    }

    public DatabaseReference getContactsReference(String email) {
        return getUserReference(email).child(CONTACTS_PATH);
    }

    public DatabaseReference getMyContactsReference() {
        return getContactsReference(getAuthUserEmail());
    }

    public DatabaseReference getOnContactsReference(String mainEmail, String childEmail) {
        String childKey = childEmail.replace(".", "_");
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(childKey);
    }


}
