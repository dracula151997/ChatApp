package com.semicolon.course.edx.chatapp.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.semicolon.course.edx.chatapp.R;
import com.semicolon.course.edx.chatapp.model.ChatMessage;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    private List<ChatMessage> chatMessages;
    private Context context;

    public ChatListAdapter(Context context, List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);

        String message = chatMessage.getMessage();
        holder.messageTextView.setText(message);

        int color = fetchColor(R.attr.colorPrimary);
        int gravity = Gravity.START;

        if (!chatMessage.isSentByMe()){
            gravity = Gravity.END;
            color = fetchColor(R.attr.colorAccent);
        }

        holder.messageTextView.setBackgroundColor(color);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.messageTextView.getLayoutParams();
        params.gravity = gravity;

        holder.messageTextView.setLayoutParams(params);


    }

    private int fetchColor(int color) {
        TypedValue typedValue = new TypedValue();
        TypedArray typedArray = context.obtainStyledAttributes(typedValue.data, new int[]{color});

        int returnColor = typedArray.getColor(0, 0);
        typedArray.recycle();

        return returnColor;


    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.txtMessage);
        }
    }

    public void add(ChatMessage chatMessage) {
        if (!alreadyInAdapter(chatMessage)) {
            chatMessages.add(chatMessage);
            this.notifyDataSetChanged();

        }
    }

    private boolean alreadyInAdapter(ChatMessage message) {
        boolean alreadyInAdapter = false;
        for (ChatMessage msg : chatMessages) {
            if (msg.getMessage().equals(message.getMessage())
                    && msg.getSender().equals(message.getSender())) {
                alreadyInAdapter = true;
                break;
            }
        }

        return alreadyInAdapter;
    }
}
