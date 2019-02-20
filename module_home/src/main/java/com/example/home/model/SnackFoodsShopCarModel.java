package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.SnackFoodsShopCarContract;

import rx.Observable;

public class SnackFoodsShopCarModel implements SnackFoodsShopCarContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> addSnackByUserId(String userId, String goodsId, String tasteId, String isDelete) {
        return RetrofitUtils.getInstance().create().addSnackByUserId(userId,goodsId,tasteId,isDelete);
    }
}
