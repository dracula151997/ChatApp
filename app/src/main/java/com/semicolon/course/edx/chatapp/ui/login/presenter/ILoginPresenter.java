package com.semicolon.course.edx.chatapp.ui.login.presenter;

import com.semicolon.course.edx.chatapp.ui.login.events.LoginEvent;

public interface ILoginPresenter {
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);

    void checkForAuthenticatedUser();
    void onEventMainThread(LoginEvent event);

    void onCreate();
    void onDestroy();




}
