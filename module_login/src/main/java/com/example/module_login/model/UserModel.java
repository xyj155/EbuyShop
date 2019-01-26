package com.example.module_login.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.module_login.contract.UserContract.Model;

import rx.Observable;

public class UserModel implements Model {
    @Override
    public Observable<BaseGson<UserGson>> userLoginByUserName(String username, String password) {
        return RetrofitUtils.getInstance().create().userLoginByUserName(username,password);
    }

    @Override
    public Observable<BaseGson<EmptyGson>> queryUserAccount(String username) {
        return RetrofitUtils.getInstance().create().queryUserAccount(username);
    }
}
