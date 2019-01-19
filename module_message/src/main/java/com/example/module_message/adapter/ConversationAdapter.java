package com.example.module_message.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.RouterUtil;
import com.example.module_message.R;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.List;

public class ConversationAdapter extends BaseQuickAdapter<RecentContact, BaseViewHolder> {
    public ConversationAdapter(@Nullable List<RecentContact> data) {
        super(R.layout.abc_ry_conversation_recent_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecentContact item) {
        NimUserInfo user = NIMClient.getService(UserService.class).getUserInfo(item.getFromAccount());
        helper.setText(R.id.iv_username, user.getAccount())
                .setText(R.id.tv_recent_msg, item.getContent())
                .setOnClickListener(R.id.ll_contact, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.getInstance().build(RouterUtil.SHOPSERVICE).navigation();
                    }
                });
        GlideUtil.loadRoundCornerAvatarImage(user.getAvatar(),(ImageView) helper.getView(R.id.iv_avatar),10);
//        Glide.with(MyApp.getInstance()).asBitmap().apply(new).load(user.getAvatar()).into((ImageView) helper.getView(R.id.iv_avatar));
    }
}
