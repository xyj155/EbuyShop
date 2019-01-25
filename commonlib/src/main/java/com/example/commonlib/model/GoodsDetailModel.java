package com.example.commonlib.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.gson.SubmitOrderGson;
import com.example.commonlib.http.RetrofitUtils;

import rx.Observable;

public class GoodsDetailModel implements GoodsDetailContract.Model {
    @Override
    public Observable<BaseGson<GoodsDetailGson>> getGoodsDetailById(String goodsId) {
        return RetrofitUtils.getInstance().create().getGoodsDetailById(goodsId);
    }

    @Override
    public Observable<BaseGson<SubmitOrderGson>> insertUserOrder(String userId, String goodsId) {
        return RetrofitUtils.getInstance().create().insertUserOrder(userId,goodsId);
    }
}
