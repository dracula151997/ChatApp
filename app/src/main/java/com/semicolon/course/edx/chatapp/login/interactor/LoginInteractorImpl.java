package com.semicolon.course.edx.chatapp.login.interactor;

import com.semicolon.course.edx.chatapp.login.repository.ILoginRepository;
import com.semicolon.course.edx.chatapp.login.repository.LoginRepositoryImpl;

public class LoginInteractorImpl implements ILoginInteractor {
    private ILoginRepository repository;

    public LoginInteractorImpl() {
        this.repository = new LoginRepositoryImpl();
    }

    @Override
    public void checkAlreadyAuthenticated() {
        repository.checkAlreadyAuthenticated();

    }

    @Override
    public void doSignIn(String email, String password) {
        repository.signIn(email, password);

    }

    @Override
    public void doSignUp(String email, String password) {
        repository.signUp(email, password);

    }
}
