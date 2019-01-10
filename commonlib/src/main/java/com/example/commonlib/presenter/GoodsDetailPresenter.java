package com.example.commonlib.presenter;

import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.GoodsDetailContract;

public class GoodsDetailPresenter extends BasePresenter<GoodsDetailContract.View> implements GoodsDetailContract.Presenter {

    public GoodsDetailPresenter(GoodsDetailContract.View mMvpView) {
        super(mMvpView);
    }
}
