package com.example.commonlib.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.gson.SubmitOrderGson;
import com.example.commonlib.http.RetrofitUtils;

import rx.Observable;

public class GoodsDetailModel implements GoodsDetailContract.Model {
    @Override
    public Observable<BaseGson<GoodsDetailGson>> getGoodsDetailById(String goodsId,String  userId) {
        return RetrofitUtils.getInstance().create().getGoodsDetailById(goodsId,userId);
    }

    @Override
    public Observable<BaseGson<SubmitOrderGson>> insertUserOrder(String userId, String goodsId) {
        return RetrofitUtils.getInstance().create().insertUserOrder(userId,goodsId);
    }

    @Override
    public Observable<BaseGson<EmptyGson>> addUserCollection(String userId, String goodsId,String  isDelete) {
        return RetrofitUtils.getInstance().create().addUserCollection(userId,goodsId,isDelete);
    }
}
