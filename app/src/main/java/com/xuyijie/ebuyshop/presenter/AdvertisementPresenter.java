package com.xuyijie.ebuyshop.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.AdvertisementGson;
import com.example.commonlib.gson.PopAdvertisementGson;
import com.example.commonlib.http.BaseObserver;
import com.xuyijie.ebuyshop.contract.AdvertisementContract;
import com.xuyijie.ebuyshop.model.AdvertisementModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AdvertisementPresenter extends BasePresenter<AdvertisementContract.View> implements AdvertisementContract.Presenter {

    public AdvertisementPresenter(AdvertisementContract.View mMvpView) {
        super(mMvpView);
    }

    private AdvertisementModel advertisementModel = new AdvertisementModel();

    @Override
    public void queryFlashAdvertisement() {
        advertisementModel.queryFlashAdvertisement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<AdvertisementGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<AdvertisementGson> advertisementGsonBaseGson) {
                        if (advertisementGsonBaseGson.isStatus()) {
                            mMvpView.loadAdvertisement(advertisementGsonBaseGson.getData().get(0));
                        } else {
                            mMvpView.loadEmpty();
                        }

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    @Override
    public void queryPopWindowAd() {
        advertisementModel.queryPopWindowAd()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<PopAdvertisementGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<PopAdvertisementGson> advertisementGsonBaseGson) {
                        if (advertisementGsonBaseGson.isStatus()) {
                            mMvpView.queryPopWindowAd(advertisementGsonBaseGson.getData());
                        }

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
