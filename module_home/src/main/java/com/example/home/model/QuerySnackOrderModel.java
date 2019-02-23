package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.SnackOrderGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.QuerySnackOrderContract;

import rx.Observable;

public class QuerySnackOrderModel implements QuerySnackOrderContract.Model {
    @Override
    public Observable<BaseGson<SnackOrderGson>> queryUserSnackOrderListByUserId(String userId, String page, String limit) {
        return RetrofitUtils.getInstance().create().queryUserSnackOrderListByUserId(userId,page,limit);
    }
}
