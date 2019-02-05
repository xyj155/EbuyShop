package com.example.home.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.SecondHandsGoodsGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.SecondHandTradingMarketContract;
import com.example.home.model.SecondHandTradingMarketModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SecondHandTradingMarketPresenter extends BasePresenter<SecondHandTradingMarketContract.View> implements SecondHandTradingMarketContract.Presenter {

    public SecondHandTradingMarketPresenter(SecondHandTradingMarketContract.View mMvpView) {
        super(mMvpView);
    }

    private SecondHandTradingMarketModel secondHandTradingMarketModel = new SecondHandTradingMarketModel();

    @Override
    public void addSecondHanding(Map<String, RequestBody> partMap, List<MultipartBody.Part> file) {
        mMvpView.showDialog("");
        secondHandTradingMarketModel.addSecondHanding(partMap, file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.uploadSuccess(emptyGsonBaseGson.isStatus());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                        mMvpView.uploadSuccess(false);
                    }
                });
    }

    @Override
    public void queryAllSecondGoods(String limit, String page) {
        mMvpView.showDialog("");
        secondHandTradingMarketModel.queryAllSecondGoods(limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SecondHandsGoodsGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SecondHandsGoodsGson> emptyGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.queryAllSecondGoods(emptyGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                        mMvpView.uploadSuccess(false);
                    }
                });
    }

    @Override
    public void loadMoreData(String limit, String page) {
        mMvpView.showDialog("");
        secondHandTradingMarketModel.queryAllSecondGoods(limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SecondHandsGoodsGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SecondHandsGoodsGson> emptyGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.loadMoreData(emptyGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                        mMvpView.uploadSuccess(false);
                    }
                });
    }
}
