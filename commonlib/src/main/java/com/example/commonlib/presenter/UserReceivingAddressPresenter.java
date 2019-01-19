package com.example.commonlib.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.UserReceivingAddressContract;
import com.example.commonlib.gson.UserReceiveAddressGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.model.UserReceivingAddressModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserReceivingAddressPresenter extends BasePresenter<UserReceivingAddressContract.View> implements UserReceivingAddressContract.Presenter {
    public UserReceivingAddressPresenter(UserReceivingAddressContract.View mMvpView) {
        super(mMvpView);
    }

    private UserReceivingAddressModel userReceivingAddressModel = new UserReceivingAddressModel();

    @Override
    public void queryUserReceiveAddress(String userId) {
        mMvpView.showDialog("");
        userReceivingAddressModel.queryUserReceiveAddress(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserReceiveAddressGson>>() {
                    @Override
                    public void onCompleted() {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onNext(BaseGson<UserReceiveAddressGson> userReceiveAddressGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.loadUserReceiveAddress(userReceiveAddressGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
