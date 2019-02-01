package com.example.kind.presenter;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.KindItemGson;
import com.example.commonlib.http.BaseObserver;

import com.example.kind.contract.KindContract;
import com.example.kind.model.KindModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class KindPresenter extends BasePresenter<KindContract.View> implements KindContract.Presenter {
    private KindModel kindModel = new KindModel();
    private static final String TAG = "KindPresenter";
//private KindContract.View view;

    public KindPresenter(KindContract.View mMvpView) {
        super(mMvpView);
    }

//    public KindPresenter(KindContract.View view) {
//        this.view = view;
//    }

    @Override
    public void getGoodsList() {
        Log.i(TAG, "getGoodsList: ");
        mMvpView.showDialog("别着急...");
        kindModel.getGoodsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<KindItemGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<KindItemGson> kindItemGsonBaseGson) {
                        mMvpView.setGoodsList(kindItemGsonBaseGson.getData());
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });

    }

    @Override
    public void getGoodsItemList(int pid) {
        mMvpView.showDialog("加载中");
        kindModel.getGoodsListItem(pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<KindItemGson>>() {
                    @Override
                    public void onCompleted() {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onNext(BaseGson<KindItemGson> kindItemGsonBaseGson) {
                        mMvpView.setGoodsItemList(kindItemGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
