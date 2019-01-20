package com.example.user.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.CouponGson;
import com.example.commonlib.http.BaseObserver;
import com.example.user.contract.UserCouponContract;
import com.example.user.model.UserCouponModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserCouponPresenter extends BasePresenter<UserCouponContract.View> implements UserCouponContract.Presenter {
    public UserCouponPresenter(UserCouponContract.View mMvpView) {
        super(mMvpView);
    }

    private UserCouponModel couponModel = new UserCouponModel();

    @Override
    public void queryUserCouponList(String userId) {
        mMvpView.showDialog("");
        couponModel.queryUserCouponList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<CouponGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<CouponGson> couponGsonBaseGson) {
                        mMvpView.loadCouponList(couponGsonBaseGson.getData());
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
