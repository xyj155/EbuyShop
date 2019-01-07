package com.example.commonlib.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.contract.GoodsSortedContract;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.http.RetrofitUtils;

import rx.Observable;

public class GoodsSortedModel implements GoodsSortedContract.Model {
    @Override
    public Observable<BaseGson<GoodsGson>> getGoodsListByKind(String kind,String type,String isacs) {
        return RetrofitUtils.getInstance().create().getGoodsListByKind(kind,type,isacs);
    }
}
