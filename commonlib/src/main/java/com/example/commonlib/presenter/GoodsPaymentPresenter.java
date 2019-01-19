package com.example.commonlib.presenter;

import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.GoodsPaymentContract;

public class GoodsPaymentPresenter extends BasePresenter<GoodsPaymentContract.View> implements GoodsPaymentContract.Presenter {

    public GoodsPaymentPresenter(GoodsPaymentContract.View mMvpView) {
        super(mMvpView);
    }
}
