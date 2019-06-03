package com.semicolon.course.edx.chatapp.ui.chat.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.semicolon.course.edx.chatapp.R;
import com.semicolon.course.edx.chatapp.adapter.ChatListAdapter;
import com.semicolon.course.edx.chatapp.model.ChatMessage;
import com.semicolon.course.edx.chatapp.ui.chat.presenter.ChatPresenter;
import com.semicolon.course.edx.chatapp.ui.chat.presenter.ChatPresenterImpl;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity implements ChatView {
    public static final String EMAIL_KEY = "email";
    public static final String ONLINE_KEY = "online";
    @BindView(R.id.username_tv)
    TextView usernameTv;
    @BindView(R.id.status_tv)
    TextView statusTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.chat_recycler)
    RecyclerView chatRecycler;
    @BindView(R.id.message_et)
    EditText messageEt;

    private ChatPresenter presenter;

    private ChatListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        presenter = new ChatPresenterImpl(this);
        presenter.onCreate();

        setSupportActionBar(toolbar);
        Intent in = getIntent();
        setupToolbarData(in);

        setupAdapter();
        setupRecyclerView();
    }

    private void setupToolbarData(Intent in) {
        String email = in.getStringExtra(EMAIL_KEY);
        boolean online = in.getBooleanExtra(ONLINE_KEY, false);

        String status = online ? "online" : "offline";
        usernameTv.setText(email);
        statusTv.setText(status);

        int color = online ? Color.GREEN : Color.RED;
        statusTv.setTextColor(color);

        presenter.setReceiver(email);
    }

    private void setupRecyclerView() {
        chatRecycler.setLayoutManager(new LinearLayoutManager(this));
        chatRecycler.setAdapter(adapter);
    }

    private void setupAdapter() {
        adapter = new ChatListAdapter(this, new ArrayList<>());
    }

    @Override
    public void onMessageRecieved(ChatMessage msg) {
        adapter.add(msg);
        chatRecycler.scrollToPosition(adapter.getItemCount() - 1);

    }

    @OnClick(R.id.send_btn)
    @Override
    public void sendMessage() {
        presenter.sendMessage(messageEt.getText().toString());
        messageEt.setText("");

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_log_out){

        }
        return super.onOptionsItemSelected(item);
    }
}
