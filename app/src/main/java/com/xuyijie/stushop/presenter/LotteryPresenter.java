package com.xuyijie.stushop.presenter;

import android.util.Log;

import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.Lottery;
import com.example.commonlib.http.BaseObserver;
import com.xuyijie.stushop.contract.LotteryContract;
import com.xuyijie.stushop.model.LotteryModel;


import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LotteryPresenter extends BasePresenter<LotteryContract.View> implements LotteryContract.Presenter {
    private LotteryModel lotteryModel = new LotteryModel();

    public LotteryPresenter(LotteryContract.View mMvpView) {
        super(mMvpView);
    }

    @Override
    public void queryLottery(String appId) {

        lotteryModel.queryLottery(appId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Lottery>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Lottery lottery) {
                        mMvpView.getLottery(lottery);
                    }

                    @Override
                    public void onError(String error) {
                        Log.i(TAG, "onError: "+error);
                    }
                });
    }

    private static final String TAG = "LotteryPresenter";}
