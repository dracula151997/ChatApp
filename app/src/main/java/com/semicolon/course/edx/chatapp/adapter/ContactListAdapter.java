package com.semicolon.course.edx.chatapp.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.semicolon.course.edx.chatapp.R;
import com.semicolon.course.edx.chatapp.lib.ImageLoader;
import com.semicolon.course.edx.chatapp.listeners.OnItemClickListener;
import com.semicolon.course.edx.chatapp.model.User;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    private List<User> contactList;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;

    public ContactListAdapter(List<User> contactList, ImageLoader imageLoader, OnItemClickListener clickListener) {
        this.contactList = contactList;
        this.imageLoader = imageLoader;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int index) {
        User user = contactList.get(index);
        holder.setClickListener(user, clickListener);

        String email = user.getEmail();
        boolean online = user.isOnline();

        String status = online ? "online" : "offline";
        int color = online ? Color.GREEN : Color.RED;

        holder.username.setText(email);
        holder.status.setText(status);
        holder.status.setTextColor(color);


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username_tv);
            status = itemView.findViewById(R.id.status_tv);
        }

        public void setClickListener(User user, OnItemClickListener listener) {
            itemView.setOnLongClickListener(v -> {
                listener.onItemLongClick(user);
                return false;
            });

            itemView.setOnClickListener(v -> listener.onItemClick(user));
        }
    }

    public void add(User user) {
        if (!alreadyInAdapter(user)) {
            contactList.add(user);
            notifyDataSetChanged();

        }
    }

    public void remove(User user) {
        int index = getPositionByUserName(user.getEmail());
        contactList.remove(index);
        notifyItemRemoved(index);

    }

    public void update(User user){
        int pos = getPositionByUserName(user.getEmail());
        contactList.set(pos, user);
        notifyDataSetChanged();
    }

    private int getPositionByUserName(String email) {
        int position = 0;
        for (User user : contactList) {
            if (user.getEmail().equals(email)) {
                break;
            }

            position++;
        }

        return position;
    }

    private boolean alreadyInAdapter(User newUser) {
        boolean alreadyInAdapter = false;
        for (User user : contactList) {
            if (user.getEmail().equals(newUser.getEmail())) {
                alreadyInAdapter = true;
            }
        }

        return alreadyInAdapter;
    }
}
