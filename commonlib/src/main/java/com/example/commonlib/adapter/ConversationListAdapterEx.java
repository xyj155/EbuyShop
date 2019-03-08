package com.example.commonlib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.commonlib.R;

import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.model.Conversation;

public class ConversationListAdapterEx extends ConversationListAdapter {
    private Context context;

    public ConversationListAdapterEx(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected View newView(Context context, int position, ViewGroup group) {
        return super.newView(context, position, group);
    }

    @Override
    protected void bindView(View v, int position, UIConversation data) {
        if (data.getConversationType().equals(Conversation.ConversationType.PRIVATE))
//            data.setUnreadType(UIConversation.UnreadRemindType.REMIND_ONLY);
        {
            View inflate = LayoutInflater.from(context).inflate(R.layout.ry_conversation_list_item, null);
            TextView tvUsername = inflate.findViewById(R.id.tv_username);
        tvUsername.setText(data.getUIConversationTitle());
        }
        super.bindView(v, position, data);
    }
}