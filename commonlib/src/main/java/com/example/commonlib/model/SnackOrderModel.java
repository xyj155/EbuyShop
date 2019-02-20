package com.example.commonlib.model;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.contract.SnackOrderContract;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.SnackOrderDetailGson;
import com.example.commonlib.http.RetrofitUtils;

import rx.Observable;

public class SnackOrderModel implements SnackOrderContract.Model {

    @Override
    public Observable<BaseGson<SnackOrderDetailGson>> querySnackFoodsOrder(String userId, String orderNum) {
        return RetrofitUtils.getInstance().create().querySnackFoodsOrder(userId,orderNum);
    }

    private static final String TAG = "SnackOrderModel";
    @Override
    public Observable<BaseGson<EmptyGson>> submitUserSnackOrderByUserId(String userId, String orderNum, String token,String message,String addressId) {
        Log.i(TAG, "submitUserSnackOrderByUserId:userId= "+userId+" orderNum="+ orderNum+" token="+ token+" message="+ message);
        return RetrofitUtils.getInstance().create().submitUserSnackOrderByUserId(userId,orderNum,token,message,addressId);
    }
}
