package com.example.module_message.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.SystemMessageGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.module_message.contract.MessageContract;

import rx.Observable;

public class MessageModel implements MessageContract.Model {
    @Override
    public Observable<BaseGson<SystemMessageGson>> querySystemPushMessage(String page) {
        return RetrofitUtils.getInstance().create().querySystemPushMessage(page);
    }

}
