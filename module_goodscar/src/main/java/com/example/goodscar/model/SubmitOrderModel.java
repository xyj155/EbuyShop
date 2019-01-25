package com.example.goodscar.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.SubmitOrderItemGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.goodscar.contract.SubmitOrderContract;

import rx.Observable;

public class SubmitOrderModel implements SubmitOrderContract.Model {
    @Override
    public Observable<BaseGson<SubmitOrderItemGson>> submitUserOrder(String userId,String goodsId) {
        return RetrofitUtils.getInstance().create().submitUserOrder(userId,goodsId);
    }
}
