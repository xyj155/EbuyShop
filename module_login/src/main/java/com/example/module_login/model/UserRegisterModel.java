package com.example.module_login.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.module_login.contract.UserRegisterContract;

import rx.Observable;

public class UserRegisterModel implements UserRegisterContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> userRegister(String userId, String count, String goodsId, String age, String school) {
        return RetrofitUtils.getInstance().create().userRegister(userId,count,goodsId,age,school);
    }
}
