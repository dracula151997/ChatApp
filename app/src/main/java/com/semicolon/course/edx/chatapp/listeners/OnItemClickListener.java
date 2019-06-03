package com.semicolon.course.edx.chatapp.listeners;

import com.semicolon.course.edx.chatapp.model.User;

public interface OnItemClickListener {
    void onItemLongClick(User user);
    void onItemClick(User user);
}
