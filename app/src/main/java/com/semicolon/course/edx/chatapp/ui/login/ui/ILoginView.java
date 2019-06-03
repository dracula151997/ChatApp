package com.semicolon.course.edx.chatapp.ui.login.ui;

public interface ILoginView {
    void showProgress();
    void hideProgress();

    void enableInputs();
    void disableInputs();

    void handleSignIn();
    void handleSignUp();

    void loginError(String error);
    void newUserError(String error);

    void navigateToMainScreen();



}
