package com.semicolon.course.edx.chatapp;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.semicolon.course.edx.chatapp.lib.GlideImageLoader;
import com.semicolon.course.edx.chatapp.lib.ImageLoader;

public class ChatApplication extends Application {
    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();
        setupImageLoader();
    }

    private void setupImageLoader() {
        imageLoader = new GlideImageLoader(this);

    }

    private void setupFirebase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
