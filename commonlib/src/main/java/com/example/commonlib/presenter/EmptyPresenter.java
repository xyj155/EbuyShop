package com.example.commonlib.presenter;

import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.EmptyContract;

public class EmptyPresenter extends BasePresenter<EmptyContract.View> implements EmptyContract.Presenter {

    public EmptyPresenter(EmptyContract.View mMvpView) {
        super(mMvpView);
    }
}
