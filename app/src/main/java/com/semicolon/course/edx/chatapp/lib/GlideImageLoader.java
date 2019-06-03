package com.semicolon.course.edx.chatapp.lib;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class GlideImageLoader implements ImageLoader {
    private RequestManager glideRequestManager;

    public GlideImageLoader(Context context) {
        glideRequestManager = Glide.with(context);
    }

    @Override
    public void load(ImageView imageView, String url) {
        glideRequestManager.load(url)
                .into(imageView);


    }
}
