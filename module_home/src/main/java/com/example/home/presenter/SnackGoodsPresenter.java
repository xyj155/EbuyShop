package com.example.home.presenter;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.SnackGson;
import com.example.commonlib.gson.SnackKindGson;
import com.example.commonlib.gson.SnackShopCarGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.SnackGoodsContract;
import com.example.home.model.SnackGoodsModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SnackGoodsPresenter extends BasePresenter<SnackGoodsContract.View> implements SnackGoodsContract.Presenter {

    public SnackGoodsPresenter(SnackGoodsContract.View mMvpView) {
        super(mMvpView);
    }

    private SnackGoodsModel snackGoodsModel = new SnackGoodsModel();

    @Override
    public void queryAllSnack() {
        mMvpView.showDialog("");
        snackGoodsModel.queryAllSnack()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SnackKindGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SnackKindGson> snackKindGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.queryAllSnack(snackKindGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    @Override
    public void querySnackByKindId(String goodsKind,String userId) {
        mMvpView.showDialog("");
        snackGoodsModel.querySnackByKindId(goodsKind,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SnackGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SnackGson> snackKindGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.querySnackByKindId(snackKindGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    @Override
    public void queryUserShopCarAllSnack(String userId) {
        mMvpView.showDialog("");
        snackGoodsModel.queryUserAllSnack(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SnackShopCarGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SnackShopCarGson> snackKindGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.queryUserSnackShopCarAllSnack(snackKindGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    @Override
    public void submitSnackFoodsOrder(String userId, String token) {
        mMvpView.showDialog("");
        snackGoodsModel.submitSnackFoodsOrder(userId,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> snackKindGsonBaseGson) {
                        Log.i(TAG, "onNext: "+snackKindGsonBaseGson);
                        mMvpView.hideDialog();
                        mMvpView.submitUserOrder(snackKindGsonBaseGson.isStatus(),snackKindGsonBaseGson.getMsg());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    private static final String TAG = "SnackGoodsPresenter";
}
