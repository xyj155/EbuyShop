package com.example.commonlib.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.contract.UserReceivingAddressContract;
import com.example.commonlib.gson.UserReceiveAddressGson;
import com.example.commonlib.http.RetrofitUtils;

import rx.Observable;

public class UserReceivingAddressModel implements UserReceivingAddressContract.Model {
    @Override
    public Observable<BaseGson<UserReceiveAddressGson>> queryUserReceiveAddress(String userId) {
        return RetrofitUtils.getInstance().create().queryUserUserAddress(userId);
    }
}
