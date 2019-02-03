package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.NewestShelfGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.NewUpperShelfContract;

import rx.Observable;

public class NewUpperShelfModel implements NewUpperShelfContract.Model {
    @Override
    public Observable<BaseGson<NewestShelfGson>> newUpperShelf(String date) {
        return RetrofitUtils.getInstance().create().newUpperShelf(date);
    }
}
