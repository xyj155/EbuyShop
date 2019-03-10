package com.example.home.presenter;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.TimeGoodsGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.TimeFlashContract;
import com.example.home.model.TimeFlashModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TimeFlashPresenter extends BasePresenter<TimeFlashContract.View> implements TimeFlashContract.Presenter {

    private TimeFlashModel timeFlashModel = new TimeFlashModel();

    public TimeFlashPresenter(TimeFlashContract.View mMvpView) {
        super(mMvpView);
    }

    private static final String TAG = "TimeFlashPresenter";
    @Override
    public void queryGoodsTime(String timeId) {
        Log.i(TAG, "queryGoodsTime: "+timeId);
        mMvpView.showDialog("");
        timeFlashModel.queryGoodsTime(timeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<TimeGoodsGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<TimeGoodsGson> timeGoodsGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (timeGoodsGsonBaseGson.getSingleData() != null)
                            mMvpView.queryTimeGoods(timeGoodsGsonBaseGson.getSingleData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
