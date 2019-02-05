package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.SecondHandsGoodsGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.SecondHandTradingMarketContract;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class SecondHandTradingMarketModel implements SecondHandTradingMarketContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> addSecondHanding(Map<String, RequestBody> partMap, List<MultipartBody.Part> file) {

        return RetrofitUtils.getInstance().create().addSecondHanding(partMap, file);
    }

    @Override
    public Observable<BaseGson<SecondHandsGoodsGson>> queryAllSecondGoods(String limit, String page) {
        return RetrofitUtils.getInstance().create().queryAllSecondGoods(limit,page);
    }
}
