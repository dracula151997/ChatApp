package com.semicolon.course.edx.chatapp.ui.login.presenter;

import com.semicolon.course.edx.chatapp.lib.EventBus;
import com.semicolon.course.edx.chatapp.lib.GreenRobotEventBus;
import com.semicolon.course.edx.chatapp.ui.login.events.LoginEvent;
import com.semicolon.course.edx.chatapp.ui.login.interactor.ILoginInteractor;
import com.semicolon.course.edx.chatapp.ui.login.interactor.LoginInteractorImpl;
import com.semicolon.course.edx.chatapp.ui.login.ui.ILoginView;

import org.greenrobot.eventbus.Subscribe;


public class LoginPresenterImp implements ILoginPresenter {
    private EventBus eventBus;
    private ILoginView loginView;
    private ILoginInteractor interactor;

    public LoginPresenterImp(ILoginView loginView) {
        this.loginView = loginView;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new LoginInteractorImpl();
    }

    @Override
    public void validateLogin(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }

        //do sign in
        interactor.doSignIn(email, password);


    }

    @Override
    public void registerNewUser(String email, String password) {
        if (loginView != null) {
            loginView.showProgress();
            loginView.disableInputs();
        }

        interactor.doSignUp(email, password);

        //do new register

    }

    @Override
    public void checkForAuthenticatedUser() {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }

        //check authentication.
        interactor.checkAlreadyAuthenticated();

    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()) {
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
        }

    }

    private void onSignUpError(String errorMessage) {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.newUserError(errorMessage);
        }
    }

    private void onSignUpSuccess() {
        if (loginView != null) {
            loginView.navigateToMainScreen();
        }
    }

    private void onSignInSuccess() {
        if (loginView != null) {
            loginView.navigateToMainScreen();
        }
    }

    private void onSignInError(String errorMessage) {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(errorMessage);
        }
    }

    @Override
    public void onCreate() {
        eventBus.register(this);

    }

    @Override
    public void onDestroy() {
        loginView = null;
        eventBus.unregister(this);

    }
}
