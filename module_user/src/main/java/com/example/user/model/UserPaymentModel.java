package com.example.user.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.UserPaymentGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.user.contract.UserPaymentContract;

import rx.Observable;

public class UserPaymentModel implements UserPaymentContract.Model {
    @Override
    public Observable<BaseGson<UserPaymentGson>> queryUserOrderCount(String userId) {
        return RetrofitUtils.getInstance().create().queryUserOrderCount(userId);
    }
}
