package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.SnackGson;
import com.example.commonlib.gson.SnackKindGson;
import com.example.commonlib.gson.SnackShopCarGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.SnackGoodsContract;

import rx.Observable;

public class SnackGoodsModel implements SnackGoodsContract.Model {
    @Override
    public Observable<BaseGson<SnackKindGson>> queryAllSnack() {
        return RetrofitUtils.getInstance().create().queryAllSnackKind();
    }

    @Override
    public Observable<BaseGson<SnackGson>> querySnackByKindId(String goodsKind,String userId) {
        return RetrofitUtils.getInstance().create().querySnackByKindId(goodsKind,userId);
    }

    @Override
    public Observable<BaseGson<SnackShopCarGson>> queryUserAllSnack(String userId) {
        return RetrofitUtils.getInstance().create().queryUserAllSnack(userId);
    }

    @Override
    public Observable<BaseGson<EmptyGson>> submitSnackFoodsOrder(String userId, String token) {
        return RetrofitUtils.getInstance().create().submitSnackFoodsOrder(userId,token);
    }
}
