package com.example.module_message.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.RouterUtil;
import com.example.module_message.R;

import java.util.List;

import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

public class ConversationAdapter extends BaseQuickAdapter<Conversation, BaseViewHolder> {
    public ConversationAdapter(@Nullable List<Conversation> data) {
        super(R.layout.abc_ry_conversation_recent_item, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Conversation item) {
        if (item.getLatestMessage() != null) {
            final UserInfo targetInfo = (UserInfo) item.getTargetInfo();
            TextContent content = (TextContent) item.getLatestMessage().getContent();
            helper.setText(R.id.iv_username, item.getTitle())
                    .setText(R.id.tv_recent_msg, content.getText())
                    .setOnClickListener(R.id.ll_contact, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i(TAG, "onClick: " + targetInfo.getUserName());
                            ARouter.getInstance().build(RouterUtil.SHOPSERVICE).withString("username", targetInfo.getUserName())
                                    .withString("appkey", targetInfo.getAppKey())
                                    .withString("shopName", targetInfo.getNickname()).navigation();
                        }
                    });
            targetInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                @Override
                public void gotResult(int i, String s, Bitmap bitmap) {
                    Log.i(TAG, "gotResult: " + i + s + bitmap);
                    if (i == 0) {
                        GlideUtil.loadRoundCornerAvatarImage(bitmap, (ImageView) helper.getView(R.id.iv_avatar), 10);
                    }
                }
            });
            Log.i(TAG, "convert: getAvatarFile=");
            int unReadMsgCnt = item.getUnReadMsgCnt();
            if (unReadMsgCnt > 0) {
                helper.setVisible(R.id.tv_msg_count, true)
                        .setText(R.id.tv_msg_count, unReadMsgCnt + "");
            } else {
                helper.setVisible(R.id.tv_msg_count, false);
            }
        }


    }
}
