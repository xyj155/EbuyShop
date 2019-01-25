package com.example.commonlib.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.contract.UserSubmitOrderContract;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.RetrofitUtils;

import rx.Observable;

public class UserSubmitOrderModel implements UserSubmitOrderContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> submitOrderByUserId(String userId,String address, String goodsId, String couponId, String orderNum, String userToken,String message) {
        return RetrofitUtils.getInstance().create().submitOrderByUserId(userId,address,goodsId,couponId,orderNum,userToken,message);
    }
}
