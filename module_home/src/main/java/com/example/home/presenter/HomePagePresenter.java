package com.example.home.presenter;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.BannerGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.HomeIconGson;
import com.example.commonlib.gson.HomePurseAdvertisementGson;
import com.example.commonlib.gson.HotPurseActivityGson;
import com.example.commonlib.gson.MarQueenGson;
import com.example.commonlib.gson.TimeGoodsGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.HomePageContract;
import com.example.home.entity.HomeDataEntity;
import com.example.home.model.HomePageModel;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func5;
import rx.functions.Func6;
import rx.schedulers.Schedulers;

public class HomePagePresenter extends BasePresenter<HomePageContract.View> implements HomePageContract.Presenter {
    private HomePageModel homePageModel = new HomePageModel();


    public HomePagePresenter(HomePageContract.View MvpView) {
        super(MvpView);
        mMvpView.showDialog("卖力加载中");
    }

    private static final String TAG = "HomePagePresenter";

    @Override
    public void setPurseGoodsList(final String type, String type2) {
        Observable.zip( homePageModel.getTimerGoodsList(type2), homePageModel.getHomeActivity(), homePageModel.getHomeBanner(), homePageModel.getMarqueenList(), homePageModel.queryHomePurseGoodsAdvertisement(),homePageModel.queryHomePageIcon(), new Func6<BaseGson<GoodsGson>, BaseGson<HotPurseActivityGson>, BaseGson<BannerGson>, BaseGson<MarQueenGson>, BaseGson<HomePurseAdvertisementGson>,BaseGson<HomeIconGson>, HomeDataEntity>() {
            @Override
            public HomeDataEntity call(BaseGson<GoodsGson> goodsGsonBaseGson, BaseGson<HotPurseActivityGson> hotPurseActivityGsonBaseGson, BaseGson<BannerGson> bannerGsonBaseGson, BaseGson<MarQueenGson> marQueenGsonBaseGson, BaseGson<HomePurseAdvertisementGson> homePurseAdvertisementGsonBaseGson,BaseGson<HomeIconGson> homeIconGsonBaseGson) {
                return new HomeDataEntity(goodsGsonBaseGson.getData(), hotPurseActivityGsonBaseGson.getData(), bannerGsonBaseGson.getData(), marQueenGsonBaseGson.getData(), homePurseAdvertisementGsonBaseGson.getSingleData(),homeIconGsonBaseGson.getData());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeDataEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mMvpView.hideDialog();
                        Log.i(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(HomeDataEntity homeDataEntity) {
                        mMvpView.hideDialog();
//                        mMvpView.loadPurseGoodsList(homeDataEntity.getPurseGoodsList());
                        mMvpView.loadTimeGoodsList(homeDataEntity.getTimePurseGoodsList());
                        mMvpView.loadHomeActivity(homeDataEntity.getHotPurseActivityGsons());
                        mMvpView.loadHomeBanner(homeDataEntity.getBannerGsons());
                        mMvpView.loadMarqueenList(homeDataEntity.getMarQueenGsonList());
                        mMvpView.queryHomePurseGoodsAdvertisement(homeDataEntity.getHomePurseAdvertisementGson());
                        ArrayList<HomeIconGson> homeIconGsons = new ArrayList<>();
                        mMvpView.loadHomePageIcon(homeDataEntity.getHomeIconGsons().size()>0? homeDataEntity.getHomeIconGsons() :homeIconGsons);
                    }
                });


    }

    @Override
    public void queryTimeSell() {
        Log.i(TAG, "queryTimeSell: ");
        homePageModel.queryTimeSell()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<TimeGoodsGson.TimeBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<TimeGoodsGson.TimeBean> timeBeanBaseGson) {
                        Log.i(TAG, "onNext: " + timeBeanBaseGson);
                        mMvpView.loadTimer(timeBeanBaseGson.getSingleData());
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    @Override
    public void queryPurseGoods(String page) {
        homePageModel.getPurseGoodsList("1", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodsGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<GoodsGson> goodsGsonBaseGson) {
                        mMvpView.loadPurseGoodsList(goodsGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }


}
