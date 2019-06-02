package com.semicolon.course.edx.chatapp.login.presenter;

import com.semicolon.course.edx.chatapp.login.events.LoginEvent;

public interface ILoginPresenter {
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);

    void checkForAuthenticatedUser();
    void onEventMainThread(LoginEvent event);

    void onCreate();
    void onDestroy();




}
