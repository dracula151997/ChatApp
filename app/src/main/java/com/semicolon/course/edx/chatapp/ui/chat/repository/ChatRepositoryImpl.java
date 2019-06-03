package com.semicolon.course.edx.chatapp.ui.chat.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.semicolon.course.edx.chatapp.lib.EventBus;
import com.semicolon.course.edx.chatapp.lib.FirebaseHelper;
import com.semicolon.course.edx.chatapp.lib.GreenRobotEventBus;
import com.semicolon.course.edx.chatapp.model.ChatMessage;
import com.semicolon.course.edx.chatapp.ui.chat.event.ChatEvent;

public class ChatRepositoryImpl implements ChatRepository {
    private FirebaseHelper helper;
    private ChildEventListener childEventListener;
    private String reciever;

    public ChatRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
    }

    @Override
    public void sendMessage(String msg) {
        String senderKey = helper.getAuthUserEmail().replace(".", "_");
        ChatMessage message = new ChatMessage(msg, senderKey);
        DatabaseReference chatReference = helper.getChatsReference(reciever);
        chatReference.push().setValue(message);

    }

    @Override
    public void setReceiver(String receiver) {
        this.reciever = receiver;

    }

    @Override
    public void subscribeForChatUpdates() {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    String sender = chatMessage.getSender();
                    sender = sender.replace("_", ".");
                    String currentUserEmail = helper.getAuthUserEmail();
                    chatMessage.setSentByMe(sender.equals(currentUserEmail));

                    ChatEvent event = new ChatEvent(chatMessage);
                    EventBus eventBus = GreenRobotEventBus.getInstance();
                    eventBus.post(event);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
        }

        helper.getChatsReference(reciever).addChildEventListener(childEventListener);


    }

    @Override
    public void unSupscribeForChatUpdates() {
        if (childEventListener != null)
            helper.getChatsReference(reciever).removeEventListener(childEventListener);

    }

    @Override
    public void destroyChatListener() {
        childEventListener = null;


    }

    @Override
    public void changeUserConnectionStatus(boolean online) {
        helper.changeUserConnectionStatus(online);

    }
}
