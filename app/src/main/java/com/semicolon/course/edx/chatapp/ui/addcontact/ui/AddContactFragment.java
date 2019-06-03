package com.semicolon.course.edx.chatapp.ui.addcontact.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.semicolon.course.edx.chatapp.R;
import com.semicolon.course.edx.chatapp.ui.addcontact.presenter.AddContactPresenter;
import com.semicolon.course.edx.chatapp.ui.addcontact.presenter.AddContactPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends DialogFragment implements AddContactView,
        DialogInterface.OnShowListener {


    @BindView(R.id.contact_email_et)
    EditText contactEmailEt;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    Unbinder unbinder;

    private AddContactPresenter presenter;

    public AddContactFragment() {
        // Required empty public constructor
        presenter = new AddContactPresenterImpl(this);

    }


    @Override
    public void contactAdded() {
        Toast.makeText(getContext(), "Contact is Added...", Toast.LENGTH_SHORT).show();
        dismiss();


    }

    @Override
    public void contactNotAdded() {
        contactEmailEt.setText("");
        contactEmailEt.setError("Not Added");

    }

    @Override
    public void showInput() {
        contactEmailEt.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }


    @Override
    public void onShow(DialogInterface dialog) {
        AlertDialog alertDialog  = (AlertDialog) getDialog();
        if (alertDialog != null){
            Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

            positiveButton.setOnClickListener(v -> {
                //add contact
                presenter.addContact(contactEmailEt.getText().toString());
            });

            negativeButton.setOnClickListener(v -> dismiss());
        }

        presenter.onShow();



    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Contact")
                .setPositiveButton("Add", (dialog12, which) -> {

                }).setNegativeButton("Cancel", (dialog1, which) -> {

        });

        View view = LayoutInflater.from(context).inflate(R.layout.fragment_add_contact, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(this);

        return alertDialog;
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();

    }
}
