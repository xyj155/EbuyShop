package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.SnackOrderDetailGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.SnackOrderDetailContract;

import rx.Observable;

public class SnackOrderDetailModel implements SnackOrderDetailContract.Model {
    @Override
    public Observable<BaseGson<SnackOrderDetailGson>> querySnackOrderDetail(String userId, String orderNum) {
        return RetrofitUtils.getInstance().create().querySnackOrderDetail(userId,orderNum);
    }
}
