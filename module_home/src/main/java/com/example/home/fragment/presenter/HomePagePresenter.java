package com.example.home.fragment.presenter;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.BannerGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.HotPurseActivityGson;

import com.example.home.fragment.contract.HomePageContract;
import com.example.home.fragment.entity.HomeDataEntity;
import com.example.home.fragment.model.HomePageModel;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.functions.Func4;
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
        Observable.zip(homePageModel.getPurseGoodsList(type), homePageModel.getTimerGoodsList(type2), homePageModel.getHomeActivity(), homePageModel.getHomeBanner(), new Func4<BaseGson<GoodsGson>, BaseGson<GoodsGson>, BaseGson<HotPurseActivityGson>, BaseGson<BannerGson>, HomeDataEntity>() {
            @Override
            public HomeDataEntity call(BaseGson<GoodsGson> goodsGsonBaseGson, BaseGson<GoodsGson> goodsGsonBaseGson2, BaseGson<HotPurseActivityGson> hotPurseActivityGsonBaseGson, BaseGson<BannerGson> bannerGsonBaseGson) {
                return new HomeDataEntity(goodsGsonBaseGson.getData(), goodsGsonBaseGson2.getData(),hotPurseActivityGsonBaseGson.getData(),bannerGsonBaseGson.getData());
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
                        Log.i(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(HomeDataEntity homeDataEntity) {
                        mMvpView.hideDialog();
                        mMvpView.loadPurseGoodsList(homeDataEntity.getPurseGoodsList());
                        mMvpView.loadTimeGoodsList(homeDataEntity.getTimePurseGoodsList());
                        mMvpView.loadHomeActivity(homeDataEntity.getHotPurseActivityGsons());
                        mMvpView.loadHomeBanner(homeDataEntity.getBannerGsons());
                    }
                });


    }


}
