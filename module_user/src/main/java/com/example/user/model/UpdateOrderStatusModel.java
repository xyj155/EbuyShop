package com.example.user.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.user.contract.UpdateOrderStatusContract;

import rx.Observable;

public class UpdateOrderStatusModel implements UpdateOrderStatusContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> updateOrderStatusByReceive(String userId, String orderNum) {
        return RetrofitUtils.getInstance().create().updateOrderStatusByReceive(userId,orderNum);
    }
}
