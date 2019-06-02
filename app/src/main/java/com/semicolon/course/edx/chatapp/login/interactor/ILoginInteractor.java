package com.semicolon.course.edx.chatapp.login.interactor;

public interface ILoginInteractor {
    void checkAlreadyAuthenticated();
    void doSignIn(String email, String password);
    void doSignUp(String email, String password);

}
