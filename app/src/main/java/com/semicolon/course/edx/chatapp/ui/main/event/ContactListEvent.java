package com.semicolon.course.edx.chatapp.ui.main.event;

import com.semicolon.course.edx.chatapp.model.User;

public class ContactListEvent {
    public static final int onContactAdded = 1;
    public static final int onContactRemoved = 2;
    public static final int onContactChanged = 3;

    private int type;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
