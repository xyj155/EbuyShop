package com.example.commonlib.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.http.RetrofitUtils;

import rx.Observable;

public class GoodsDetailModel implements GoodsDetailContract.Model {
    @Override
    public Observable<BaseGson<GoodsDetailGson>> getGoodsDetailById(String goodsId) {
        return RetrofitUtils.getInstance().create().getGoodsDetailById(goodsId);
    }
}
