package com.semicolon.course.edx.chatapp.ui.login.repository;

public interface ILoginRepository {
    void signIn(String email, String password);
    void signUp(String email, String password);
    void checkAlreadyAuthenticated();
}
