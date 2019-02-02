package com.example.commonlib.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.contract.GoodsCommentContract;
import com.example.commonlib.gson.GoodsCommentGson;
import com.example.commonlib.http.RetrofitUtils;

import rx.Observable;

public class GoodsCommentModel implements GoodsCommentContract.Model {
    @Override
    public Observable<BaseGson<GoodsCommentGson>> queryGoodsComment(String goodsId) {
        return RetrofitUtils.getInstance().create().queryGoodsComment(goodsId);
    }
}
