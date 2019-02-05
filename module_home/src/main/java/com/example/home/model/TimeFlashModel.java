package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.TimeGoodsGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.TimeFlashContract;

import rx.Observable;

public class TimeFlashModel implements TimeFlashContract.Model {
    @Override
    public Observable<BaseGson<TimeGoodsGson>> queryGoodsTime(String timeId) {
        return RetrofitUtils.getInstance().create().queryGoodsTime(timeId);
    }
}
