package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.GoodsSearchContract;

import rx.Observable;

public class GoodsSearchModel implements GoodsSearchContract.Model {
    @Override
    public Observable<BaseGson<GoodsGson>> querySearchByLikeWord(String userId) {
        return RetrofitUtils.getInstance().create().querySearchByLikeWord(userId);
    }
}
