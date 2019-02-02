package com.xuyijie.ebuyshop.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.AdvertisementGson;
import com.example.commonlib.http.RetrofitUtils;
import com.xuyijie.ebuyshop.contract.AdvertisementContract;

import rx.Observable;

public class AdvertisementModel implements AdvertisementContract.Model {
    @Override
    public Observable<BaseGson<AdvertisementGson>> queryFlashAdvertisement() {
        return RetrofitUtils.getInstance().create().queryFlashAdvertisement();
    }
}
