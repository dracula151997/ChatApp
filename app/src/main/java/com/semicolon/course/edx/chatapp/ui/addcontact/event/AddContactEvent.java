package com.semicolon.course.edx.chatapp.ui.addcontact.event;

public class AddContactEvent {
    private boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
