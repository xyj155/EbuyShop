package com.example.user.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.ExpressTraceGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.user.contract.ExpressTraceContract;

import rx.Observable;

public class ExpressTraceModel implements ExpressTraceContract.Model {
    @Override
    public Observable<BaseGson<ExpressTraceGson>> queryExpressByNum(String expressNum) {
        return RetrofitUtils.getInstance().create().queryExpressByNum(expressNum);
    }
}
