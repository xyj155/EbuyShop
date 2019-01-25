package com.example.user.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.UserOrderStatusGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.user.contract.UserFormStatusContract;

import java.util.List;

import rx.Observable;

public class UserFormStatusModel implements UserFormStatusContract.Model {
    @Override
    public Observable<BaseGson<List<UserOrderStatusGson>>> queryUserOrderByStatus(String userId, String status) {
        return RetrofitUtils.getInstance().create().queryUserOrderByStatus(userId,status);
    }
}
