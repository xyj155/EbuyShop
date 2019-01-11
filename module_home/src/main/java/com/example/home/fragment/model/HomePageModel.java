package com.example.home.fragment.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.BannerGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.HotPurseActivityGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.fragment.contract.HomePageContract;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomePageModel implements HomePageContract.Model {
    @Override
    public Observable<BaseGson<GoodsGson>> getTimerGoodsList(String type) {
        return RetrofitUtils.getInstance().create().queryPurseGoods(type);
    }

    @Override
    public Observable<BaseGson<GoodsGson>> getPurseGoodsList(String type) {
        return RetrofitUtils.getInstance().create().queryPurseGoods(type);
    }

    @Override
    public Observable<BaseGson<HotPurseActivityGson>> getHomeActivity() {
        return RetrofitUtils.getInstance().create().getHomeActivity();
    }

    @Override
    public Observable<BaseGson<BannerGson>> getHomeBanner() {
        return RetrofitUtils.getInstance().create().getHomeBanner();
    }
}
