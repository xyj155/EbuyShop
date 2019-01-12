package com.example.commonlib.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.netease.nimlib.sdk.msg.model.IMMessage;

public class ConversationEntity implements MultiItemEntity {
    public static final int TYPE_CLIENT_MESSAGE = 1;
    public static final int TYPE_SERVICES_MESSAGE =2;
    public static final int TYPE_EMPTY_MESSAGE =3;


    private IMMessage data;
    private int itemType;
    public ConversationEntity(int itemType, IMMessage data) {
        this.itemType = itemType;
        this.data = data;
    }
    @Override
    public int getItemType() {
        return itemType;
    }
    /**
     * @param data
     * @return
     */
    public static ConversationEntity service(IMMessage data) {
        return new ConversationEntity(TYPE_SERVICES_MESSAGE, data);
    }

    /**
     * @param data
     * @return
     */
    public static ConversationEntity client(IMMessage  data) {
        return new ConversationEntity(TYPE_CLIENT_MESSAGE, data);
    }

    public IMMessage getData() {
        return data;
    }

    public void setData(IMMessage data) {
        this.data = data;
    }
}
