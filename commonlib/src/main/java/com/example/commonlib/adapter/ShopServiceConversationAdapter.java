package com.example.commonlib.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.R;
import com.example.commonlib.entity.ConversationEntity;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.view.previewphoto.PhotoPreviewActivity;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.api.BasicCallback;

public class ShopServiceConversationAdapter extends BaseMultiItemQuickAdapter<ConversationEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private Context context;

    public ShopServiceConversationAdapter(List<ConversationEntity> data, Context context) {
        super(data);
        this.context = context;
        addItemType(ConversationEntity.TYPE_SERVICES_MESSAGE, R.layout.conversation_goods_service_right_layout);
        addItemType(ConversationEntity.TYPE_CLIENT_MESSAGE, R.layout.conversation_goods_service_left_layout);
        addItemType(ConversationEntity.TYPE_EMPTY_MESSAGE, R.layout.conversation_empty_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, ConversationEntity item) {
        int itemViewType = helper.getItemViewType();

        switch (itemViewType) {
            case ConversationEntity.TYPE_SERVICES_MESSAGE:
                Log.i(TAG, "convert: getAvatarFile" + item.getData().getFromUser().getAvatarFile());
                GlideUtil.loadRoundCornerAvatarImage(item.getData().getFromUser().getAvatarFile(), (ImageView) helper.getView(R.id.iv_avatar), 16);
                break;
            case ConversationEntity.TYPE_CLIENT_MESSAGE:
                Log.i(TAG, "convert:JMessageClient " + JMessageClient.getMyInfo().getAvatarFile());
                GlideUtil.loadRoundCornerAvatarImage(JMessageClient.getMyInfo().getAvatarFile(), (ImageView) helper.getView(R.id.iv_avatar), 16);
                break;

        }
        item.getData().setHaveRead(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i(TAG, "gotResult: " + "已读");
            }
        });

        ContentType contentType = item.getData().getContentType();
        switch (contentType) {
            case image:
                ImageView view = (ImageView) helper.getView(R.id.iv_picture);
                final ImageContent content = (ImageContent) item.getData().getContent();
                GlideUtil.loadGeneralImage(content.getLocalThumbnailPath(), view);
                Log.i(TAG, "convert: getImg_link" + content.getLocalPath());
                Log.i(TAG, "convert:getLocalThumbnailPath " + content.getLocalThumbnailPath());
                view.setVisibility(View.VISIBLE);
                helper.setOnClickListener(R.id.iv_picture, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<String> stringList=new ArrayList<>();
                        stringList.add(content.getLocalThumbnailPath());
                        Intent intent = new Intent(context, PhotoPreviewActivity.class);
                        intent.putExtra(PhotoPreviewActivity.EXTRA_PHOTOS,(ArrayList)stringList);
                        context.startActivity(intent);

                    }
                });
                break;
            case text:
                TextContent client_content = (TextContent) item.getData().getContent();
                helper.setText(R.id.tv_content, client_content.getText());//iv_picture
                break;

        }


    }
}
