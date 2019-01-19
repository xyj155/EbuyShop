package com.example.commonlib.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.contract.GoodsStyleContract;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.GoodsStyleGson;
import com.example.commonlib.http.RetrofitUtils;

import rx.Observable;

public class GoodsStyleModel implements GoodsStyleContract.Model {
    @Override
    public Observable<BaseGson<GoodsStyleGson>> queryGoodsStyle(String goodsId) {
        return RetrofitUtils.getInstance().create().queryGoodsStyle(goodsId);
    }

    @Override
    public Observable<BaseGson<EmptyGson>> addGoodsInShopCarById(String userId, String goodsId,String type) {
        return RetrofitUtils.getInstance().create().addGoodsInShopCarById(userId, goodsId,type);
    }
}
