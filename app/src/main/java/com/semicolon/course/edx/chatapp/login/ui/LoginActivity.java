package com.semicolon.course.edx.chatapp.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.semicolon.course.edx.chatapp.MainActivity;
import com.semicolon.course.edx.chatapp.R;
import com.semicolon.course.edx.chatapp.login.presenter.ILoginPresenter;
import com.semicolon.course.edx.chatapp.login.presenter.LoginPresenterImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.pass_et)
    EditText passEt;
    @BindView(R.id.sign_in_btn)
    Button signInBtn;
    @BindView(R.id.sign_up_btn)
    Button signUpBtn;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;

    private ILoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenterImp(this);
        presenter.onCreate();
    }

    @Override
    public void showProgress() {
        loginProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loginProgress.setVisibility(View.GONE);

    }

    @Override
    public void enableInputs() {
        setInputs(true);

    }

    private void setInputs(boolean enabled) {
        emailEt.setEnabled(enabled);
        passEt.setEnabled(enabled);
        signInBtn.setEnabled(enabled);
        signUpBtn.setEnabled(enabled);

    }

    @Override
    public void disableInputs() {
        setInputs(false);

    }

    @Override
    @OnClick(R.id.sign_in_btn)
    public void handleSignIn() {
        presenter.validateLogin(emailEt.getText().toString(),
                passEt.getText().toString());

    }

    @Override
    @OnClick(R.id.sign_up_btn)
    public void handleSignUp() {
        presenter.registerNewUser(emailEt.getText().toString(),
                passEt.getText().toString());

    }

    @Override
    public void loginError(String error) {
        showToast(error);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        showToast(error);

    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, MainActivity.class));

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
