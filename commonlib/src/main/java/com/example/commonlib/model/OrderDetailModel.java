package com.example.commonlib.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.contract.OrderDetailContract;
import com.example.commonlib.gson.OrderDetailGson;
import com.example.commonlib.http.RetrofitUtils;

import rx.Observable;

public class OrderDetailModel implements OrderDetailContract.Model {
    @Override
    public Observable<BaseGson<OrderDetailGson>> confirmationOrderByUserId(String userId, String goodsId) {
        return RetrofitUtils.getInstance().create().confirmationOrderByUserId(userId,goodsId);
    }
}
