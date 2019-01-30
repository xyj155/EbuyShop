package com.example.home.presenter;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.GoodsShareGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.GoodsShareContract;
import com.example.home.model.GoodsShareModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodsSharePresenter extends BasePresenter<GoodsShareContract.View> implements GoodsShareContract.Presenter {

    public GoodsSharePresenter(GoodsShareContract.View mMvpView) {
        super(mMvpView);
        mMvpView.showDialog("数据加载中...");
    }

    private GoodsShareModel shareModel = new GoodsShareModel();
    private static final String TAG = "GoodsSharePresenter";
    @Override
    public void getUserShareCommentList(String type, String page) {
        Log.i(TAG, "getUserShareCommentList: ");
        shareModel.getUserShareCommentList(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodsShareGson>>() {

                    @Override
                    public void onCompleted() {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onNext(BaseGson<GoodsShareGson> goodsShareGsonBaseGson) {
                        mMvpView.loadUserShareCommentList(goodsShareGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
