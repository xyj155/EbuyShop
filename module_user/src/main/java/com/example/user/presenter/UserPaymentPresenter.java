package com.example.user.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.UserPaymentGson;
import com.example.commonlib.http.BaseObserver;
import com.example.user.contract.UserPaymentContract;
import com.example.user.model.UserPaymentModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserPaymentPresenter extends BasePresenter<UserPaymentContract.View> implements UserPaymentContract.Presenter {

    public UserPaymentPresenter(UserPaymentContract.View mMvpView) {
        super(mMvpView);
    }

    private UserPaymentModel userModel = new UserPaymentModel();

    @Override
    public void queryUserOrderCount(String userId) {
        mMvpView.showDialog("");
        userModel.queryUserOrderCount(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserPaymentGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<UserPaymentGson> userPaymentGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.setUserOrderCount(userPaymentGsonBaseGson.getData().get(0));
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
