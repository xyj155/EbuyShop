package com.example.user.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.user.contract.UserCollectionContract;

import rx.Observable;

public class UserCollectionModel implements UserCollectionContract.Model {
    @Override
    public Observable<BaseGson<GoodsGson>> queryUserCollection(String userId) {
        return RetrofitUtils.getInstance().create().queryUserCollection(userId);
    }
}
