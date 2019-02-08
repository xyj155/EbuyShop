package com.example.home.presenter;

import com.example.commonlib.base.BasePresenter;
import com.example.home.contract.UpdateMicroShopContract;

public class UpdateMicroShopPresenter extends BasePresenter<UpdateMicroShopContract.View> implements UpdateMicroShopContract.Presenter {

    public UpdateMicroShopPresenter(UpdateMicroShopContract.View mMvpView) {
        super(mMvpView);
    }
}
