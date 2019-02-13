package com.example.commonlib.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.jpush.im.android.api.model.Message;

public class ConversationEntity implements MultiItemEntity {
    public static final int TYPE_CLIENT_MESSAGE = 1;
    public static final int TYPE_SERVICES_MESSAGE =2;
    public static final int TYPE_EMPTY_MESSAGE =3;


    private Message data;
    private int itemType;
    public ConversationEntity(int itemType, Message  data) {
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
    public static ConversationEntity service(Message  data) {
        return new ConversationEntity(TYPE_SERVICES_MESSAGE, data);
    }

    /**
     * @param data
     * @return
     */
    public static ConversationEntity client(Message   data) {
        return new ConversationEntity(TYPE_CLIENT_MESSAGE, data);
    }

    public Message  getData() {
        return data;
    }

    public void setData(Message  data) {
        this.data = data;
    }
}
