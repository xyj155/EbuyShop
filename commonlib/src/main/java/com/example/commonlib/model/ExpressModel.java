package com.example.commonlib.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.contract.ExpressContract;
import com.example.commonlib.gson.ExpressGson;
import com.example.commonlib.http.RetrofitUtils;

import rx.Observable;

public class ExpressModel implements ExpressContract.Model {
    @Override
    public Observable<BaseGson<ExpressGson>> queryExpress() {
        return RetrofitUtils.getInstance().create().queryAllExpress();
    }
}
