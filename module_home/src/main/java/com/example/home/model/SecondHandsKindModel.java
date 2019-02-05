package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.SecondKindGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.SecondHandsKindContract;

import rx.Observable;

public class SecondHandsKindModel implements SecondHandsKindContract.Model {
    @Override
    public Observable<BaseGson<SecondKindGson>> queryAllSecondKind() {
        return RetrofitUtils.getInstance().create().queryAllSecondKind();
    }
}
