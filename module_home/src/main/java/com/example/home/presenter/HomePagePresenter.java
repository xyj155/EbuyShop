package com.example.home.presenter;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.BannerGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.HotPurseActivityGson;
import com.example.commonlib.gson.MarQueenGson;
import com.example.home.contract.HomePageContract;
import com.example.home.entity.HomeDataEntity;
import com.example.home.model.HomePageModel;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func5;
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
        Observable.zip(homePageModel.getPurseGoodsList(type), homePageModel.getTimerGoodsList(type2), homePageModel.getHomeActivity(), homePageModel.getHomeBanner(), homePageModel.getMarqueenList(), new Func5<BaseGson<GoodsGson>, BaseGson<GoodsGson>, BaseGson<HotPurseActivityGson>, BaseGson<BannerGson>, BaseGson<MarQueenGson>, HomeDataEntity>() {
            @Override
            public HomeDataEntity call(BaseGson<GoodsGson> goodsGsonBaseGson, BaseGson<GoodsGson> goodsGsonBaseGson2, BaseGson<HotPurseActivityGson> hotPurseActivityGsonBaseGson, BaseGson<BannerGson> bannerGsonBaseGson, BaseGson<MarQueenGson> marQueenGsonBaseGson) {
                return new HomeDataEntity(goodsGsonBaseGson.getData(), goodsGsonBaseGson2.getData(),hotPurseActivityGsonBaseGson.getData(),bannerGsonBaseGson.getData(),marQueenGsonBaseGson.getData());
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
                        mMvpView.loadMarqueenList(homeDataEntity.getMarQueenGsonList());
                    }
                });


    }


}