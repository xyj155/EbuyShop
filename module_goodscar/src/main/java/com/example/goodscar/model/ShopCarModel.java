package com.example.goodscar.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.ShopCarGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.goodscar.contract.ShopCarContract;

import rx.Observable;

public class ShopCarModel implements ShopCarContract.Model {
    @Override
    public Observable<BaseGson<ShopCarGson>> queryUserShopCarByUid(String userId) {
        return RetrofitUtils.getInstance().create().queryUserShopCarByUid(userId);
    }
}
