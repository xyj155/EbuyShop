package com.example.commonlib.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.contract.UserMemberDateContract;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.RetrofitUtils;

import rx.Observable;

public class UserMemberDateModel implements UserMemberDateContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> judgementMember(String userId) {
        return RetrofitUtils.getInstance().create().judgementMember(userId);
    }
}
