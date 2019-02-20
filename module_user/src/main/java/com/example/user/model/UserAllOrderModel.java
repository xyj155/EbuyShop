package com.example.user.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.UserOrderStatusGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.user.contract.UserAllOrderContract;

import java.util.List;

import rx.Observable;

public class UserAllOrderModel implements UserAllOrderContract.Model {


    @Override
    public Observable<BaseGson<List<UserOrderStatusGson> >> queryUserAllOrder(String userId, String page) {
        return RetrofitUtils.getInstance().create().queryUserAllOrder(userId, page);
    }
}
