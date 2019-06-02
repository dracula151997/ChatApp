package com.semicolon.course.edx.chatapp.login.repository;

public interface ILoginRepository {
    void signIn(String email, String password);
    void signUp(String email, String password);
    void checkAlreadyAuthenticated();
}
