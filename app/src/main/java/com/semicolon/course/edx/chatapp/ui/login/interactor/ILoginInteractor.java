package com.semicolon.course.edx.chatapp.ui.login.interactor;

public interface ILoginInteractor {
    void checkAlreadyAuthenticated();
    void doSignIn(String email, String password);
    void doSignUp(String email, String password);

}
