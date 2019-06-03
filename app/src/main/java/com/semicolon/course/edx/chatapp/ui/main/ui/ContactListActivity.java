package com.semicolon.course.edx.chatapp.ui.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.semicolon.course.edx.chatapp.ChatApplication;
import com.semicolon.course.edx.chatapp.R;
import com.semicolon.course.edx.chatapp.adapter.ContactListAdapter;
import com.semicolon.course.edx.chatapp.lib.ImageLoader;
import com.semicolon.course.edx.chatapp.listeners.OnItemClickListener;
import com.semicolon.course.edx.chatapp.model.User;
import com.semicolon.course.edx.chatapp.ui.addcontact.ui.AddContactFragment;
import com.semicolon.course.edx.chatapp.ui.chat.ui.ChatActivity;
import com.semicolon.course.edx.chatapp.ui.login.ui.LoginActivity;
import com.semicolon.course.edx.chatapp.ui.main.presenter.ContactListPresenterImpl;
import com.semicolon.course.edx.chatapp.ui.main.presenter.IContactListPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactListActivity extends AppCompatActivity implements IContactListView, OnItemClickListener {

    private IContactListPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.contacts_recycler)
    RecyclerView contactsRecycler;
    @BindView(R.id.add_fb)
    FloatingActionButton addFb;

    private ContactListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new ContactListPresenterImpl(this);

        toolbar.setSubtitle(presenter.getCurrentEmail());
        setSupportActionBar(toolbar);


        presenter.onCreate();

        setupAdapter();
        setupRecyclerView();

    }

    private void setupAdapter() {
        ChatApplication app = (ChatApplication) getApplication();
        ImageLoader imageLoader = app.getImageLoader();
        adapter = new ContactListAdapter(new ArrayList<User>(), imageLoader, this);


    }

    private void setupRecyclerView() {
        contactsRecycler.setLayoutManager(new LinearLayoutManager(this));
        contactsRecycler.setHasFixedSize(true);
        contactsRecycler.setAdapter(adapter);


    }

    @Override
    public void onUserAdded(User user) {
        adapter.add(user);

    }

    @Override
    public void onUserRemoved(User user) {
        adapter.remove(user);

    }

    @Override
    public void onUserChanged(User user) {
        adapter.update(user);

    }

    @Override
    public void onItemLongClick(User user) {
        presenter.removeContact(user.getEmail());
    }

    @Override
    public void onItemClick(User user) {
        Intent in = new Intent(this, ChatActivity.class);
        in.putExtra(ChatActivity.EMAIL_KEY, user.getEmail());
        in.putExtra(ChatActivity.ONLINE_KEY, user.isOnline());
        startActivity(in);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @OnClick(R.id.add_fb)
    public void onViewClicked() {
        AddContactFragment addContactFragment = new AddContactFragment();
        addContactFragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_log_out){
            presenter.signOff();
            startActivity(new Intent(this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
