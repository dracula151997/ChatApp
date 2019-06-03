package com.semicolon.course.edx.chatapp.ui.main.ui;

import com.semicolon.course.edx.chatapp.model.User;

public interface IContactListView {
    void onUserAdded(User user);
    void onUserRemoved(User user);
    void onUserChanged(User user);
}
