package com.example.user.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.user.contract.UserOrderDeleteContract;

import rx.Observable;

public class UserOrderDeleteModel implements UserOrderDeleteContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> deleteOrderByOrderNum(String orderNum, String userId) {
        return RetrofitUtils.getInstance().create().deleteOrderByOrderNum(orderNum, userId);
    }
}
