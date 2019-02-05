package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.HomeActivityContract;

import rx.Observable;

public class HomeActivityModel implements HomeActivityContract.Model {
    @Override
    public Observable<BaseGson<GoodsGson>> queryHomeActivityByName(String activityName) {
        return RetrofitUtils.getInstance().create().queryHomeActivityByName(activityName);
    }
}
