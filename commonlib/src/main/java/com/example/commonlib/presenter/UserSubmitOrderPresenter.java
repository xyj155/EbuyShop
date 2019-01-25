package com.example.commonlib.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.UserSubmitOrderContract;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.model.UserSubmitOrderModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserSubmitOrderPresenter extends BasePresenter<UserSubmitOrderContract.View> implements UserSubmitOrderContract.Presenter {

    public UserSubmitOrderPresenter(UserSubmitOrderContract.View mMvpView) {
        super(mMvpView);
    }

    private UserSubmitOrderModel userSubmitOrderModel = new UserSubmitOrderModel();

    @Override
    public void submitOrderByUserId(String userId, String address,String goodsId, String couponId, String orderNum, String userToken,String message) {
        userSubmitOrderModel.submitOrderByUserId(userId, address,goodsId, couponId, orderNum, userToken,message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        if (emptyGsonBaseGson.isStatus()) {
                            mMvpView.submitStatus(true);
                        } else {
                            mMvpView.submitStatus(false);
                        }

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
