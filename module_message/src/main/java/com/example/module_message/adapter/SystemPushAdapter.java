package com.example.module_message.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.commonactivity.BrowserActivity;
import com.example.commonlib.gson.SystemMessageGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.util.GlideUtil;
import com.example.module_message.R;

import java.util.List;

public class SystemPushAdapter extends BaseQuickAdapter<SystemMessageGson, BaseViewHolder> {
    private Context context;
    public SystemPushAdapter(@Nullable List<SystemMessageGson> data, Context context) {
        super(R.layout.ry_system_push_item_layout, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final SystemMessageGson item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getMessage())
                .setText(R.id.tv_time, item.getUpdateTime().substring(5, 10))
        .setOnClickListener(R.id.ll_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BrowserActivity.class);
                intent.putExtra("url",RetrofitUtils.BASE_URL+item.getWebUrl());
                context.startActivity(intent);
            }
        });
        GlideUtil.loadRoundCornerAvatarImage( item.getPictureUrl(), (ImageView) helper.getView(R.id.iv_image), 8);
    }
}
