package com.example.home.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.HomeActivityContract;
import com.example.home.model.HomeActivityModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeActivityPresenter extends BasePresenter<HomeActivityContract.View> implements HomeActivityContract.Presenter {

    public HomeActivityPresenter(HomeActivityContract.View mMvpView) {
        super(mMvpView);
    }

    private HomeActivityModel homeActivityModel = new HomeActivityModel();

    @Override
    public void queryHomeActivityByName(String activityName) {
        mMvpView.showDialog("");
        homeActivityModel.queryHomeActivityByName(activityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodsGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<GoodsGson> goodsGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (goodsGsonBaseGson!=null){
                            mMvpView.queryHomeActivityByName(goodsGsonBaseGson.getData());
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
