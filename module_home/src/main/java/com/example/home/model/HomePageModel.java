package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.BannerGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.HomeIconGson;
import com.example.commonlib.gson.HomePurseAdvertisementGson;
import com.example.commonlib.gson.HotPurseActivityGson;
import com.example.commonlib.gson.MarQueenGson;
import com.example.commonlib.gson.TimeGoodsGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.HomePageContract;

import rx.Observable;

public class HomePageModel implements HomePageContract.Model {
    @Override
    public Observable<BaseGson<GoodsGson>> getTimerGoodsList(String type) {
        return RetrofitUtils.getInstance().create().queryPurseGoods(type,"1");
    }

    @Override
    public Observable<BaseGson<GoodsGson>> getPurseGoodsList(String type,String page) {
        return RetrofitUtils.getInstance().create().queryPurseGoods(type,page);
    }

    @Override
    public Observable<BaseGson<HotPurseActivityGson>> getHomeActivity() {
        return RetrofitUtils.getInstance().create().getHomeActivity();
    }

    @Override
    public Observable<BaseGson<BannerGson>> getHomeBanner() {
        return RetrofitUtils.getInstance().create().getHomeBanner();
    }

    @Override
    public Observable<BaseGson<MarQueenGson>> getMarqueenList() {
        return RetrofitUtils.getInstance().create().getMarqueenList();
    }

    @Override
    public Observable<BaseGson<TimeGoodsGson.TimeBean>> queryTimeSell() {
        return RetrofitUtils.getInstance().create().queryTimeSell();
    }

    @Override
    public Observable<BaseGson<HomePurseAdvertisementGson>> queryHomePurseGoodsAdvertisement() {
        return RetrofitUtils.getInstance().create().queryHomePurseGoodsAdvertisement();
    }

    @Override
    public Observable<BaseGson<HomeIconGson>> queryHomePageIcon() {
        return RetrofitUtils.getInstance().create().queryHomePageIcon();
    }
}
