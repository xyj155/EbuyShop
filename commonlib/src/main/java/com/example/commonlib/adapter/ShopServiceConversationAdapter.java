package com.example.commonlib.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.R;
import com.example.commonlib.entity.ConversationEntity;

import java.util.List;

public class ShopServiceConversationAdapter extends BaseMultiItemQuickAdapter<ConversationEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ShopServiceConversationAdapter(List<ConversationEntity> data) {
        super(data);
        addItemType(ConversationEntity.TYPE_SERVICES_MESSAGE, R.layout.conversation_goods_service_right_layout);
        addItemType(ConversationEntity.TYPE_CLIENT_MESSAGE, R.layout.conversation_goods_service_left_layout);
        addItemType(ConversationEntity.TYPE_EMPTY_MESSAGE, R.layout.conversation_empty_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConversationEntity item) {
        int itemViewType = helper.getItemViewType();
        switch (itemViewType) {
            case ConversationEntity.TYPE_SERVICES_MESSAGE:
//                Log.i(TAG, "convert: "+item.getData().getContent());
                helper.setText(R.id.tv_content, item.getData().getContent());
//                LeBubbleTextView bvSend = helper.getView(R.id.tv_send);
//                TextView contentTextView1 = bvSend.getContentTextView();
//                contentTextView1.setText(item.getData().getContent());
                break;
            case ConversationEntity.TYPE_CLIENT_MESSAGE:

                helper.setText(R.id.tv_content, item.getData().getContent());
//                LeBubbleTextView bvReceiver = helper.getView(R.id.tv_receiver);
//                TextView contentTextView = bvReceiver.getContentTextView();
//                contentTextView.setText(item.getData().getContent());
                break;

        }
    }
}
