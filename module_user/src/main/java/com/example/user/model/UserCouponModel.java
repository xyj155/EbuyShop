package com.example.user.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.CouponGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.user.contract.UserCouponContract;

import rx.Observable;

public class UserCouponModel implements UserCouponContract.Model {
    @Override
    public Observable<BaseGson<CouponGson>> queryUserCouponList(String userId) {
        return RetrofitUtils.getInstance().create().queryUserCouponList(userId);
    }
}
