package com.example.home.fragment.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.GoodsShareGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.fragment.contract.GoodsShareContract;

import rx.Observable;

public class GoodsShareModel implements GoodsShareContract.Model {
    @Override
    public Observable<BaseGson<GoodsShareGson>> getUserShareCommentList(String type, String page) {
        return RetrofitUtils.getInstance().create().getGoodsShareList(type, page);
    }
}
