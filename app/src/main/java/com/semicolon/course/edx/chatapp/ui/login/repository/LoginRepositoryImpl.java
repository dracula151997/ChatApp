package com.semicolon.course.edx.chatapp.ui.login.repository;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.semicolon.course.edx.chatapp.lib.EventBus;
import com.semicolon.course.edx.chatapp.lib.FirebaseHelper;
import com.semicolon.course.edx.chatapp.lib.GreenRobotEventBus;
import com.semicolon.course.edx.chatapp.ui.login.events.LoginEvent;
import com.semicolon.course.edx.chatapp.model.User;

public class LoginRepositoryImpl implements ILoginRepository {
    private FirebaseHelper firebaseHelper;
    private DatabaseReference databaseReference;
    private DatabaseReference myUserRef;

    public LoginRepositoryImpl() {
        firebaseHelper = FirebaseHelper.getInstance();
        databaseReference = firebaseHelper.getDatabaseReference();
        myUserRef = firebaseHelper.getMyUserReference();
    }

    @Override
    public void signIn(String email, String password) {
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        myUserRef = firebaseHelper.getMyUserReference();
                        myUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                initSignIn(dataSnapshot);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                postEvent(LoginEvent.onSignInError, databaseError.getMessage());


                            }
                        });


                    }).addOnFailureListener(e -> postEvent(LoginEvent.onSignInError, e.getMessage()));
        } catch (Exception e) {
            postEvent(LoginEvent.onSignInError, e.getMessage());
        }


    }

    private void initSignIn(DataSnapshot dataSnapshot) {
        User currentUser = dataSnapshot.getValue(User.class);
        if (currentUser == null) {
            registerNewUser();
        }

        firebaseHelper.changeUserConnectionStatus(User.ONLINE);
        postEvent(LoginEvent.onSignUpSuccess);

    }

    private void registerNewUser() {
        String email = firebaseHelper.getAuthUserEmail();
        if (email != null) {
            User user = new User(email, true, null);
            myUserRef.setValue(user);
        }
    }

    private void postEvent(int type, String message) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (message != null) {
            loginEvent.setErrorMessage(message);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);


    }

    private void postEvent(int type) {
        postEvent(type, null);
    }

    @Override
    public void signUp(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    postEvent(LoginEvent.onSignUpSuccess);
                    signIn(email, password);
                }).addOnFailureListener(e -> postEvent(LoginEvent.onSignUpError, e.getMessage()));

    }

    @Override
    public void checkAlreadyAuthenticated() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            myUserRef = firebaseHelper.getMyUserReference();
            myUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    initSignIn(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    postEvent(LoginEvent.onSignInError, databaseError.getMessage());

                }
            });
        } else {
            postEvent(LoginEvent.onFailedToRecoverSession);
        }

    }
}
