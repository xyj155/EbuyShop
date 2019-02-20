package com.example.user.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.UserOrderStatusGson;
import com.example.commonlib.http.BaseObserver;
import com.example.user.contract.UserAllOrderContract;
import com.example.user.model.UserAllOrderModel;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserAllOrderPresenter extends BasePresenter<UserAllOrderContract.View> implements UserAllOrderContract.Presenter {
    private UserAllOrderModel userAllOrderModel = new UserAllOrderModel();

    public UserAllOrderPresenter(UserAllOrderContract.View mMvpView) {
        super(mMvpView);
    }

    @Override
    public void queryUserAllOrder(String userId, String page) {
        mMvpView.showDialog("");
        userAllOrderModel.queryUserAllOrder(userId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<List<UserOrderStatusGson>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<List<UserOrderStatusGson>> userOrderStatusGsonBaseGson) {
                        mMvpView.hideDialog();

                        mMvpView.loadUserAllOrders(userOrderStatusGsonBaseGson.getData());

                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    @Override
    public void loadUserMoreOrders(String userId, String page) {
        mMvpView.showDialog("");
        userAllOrderModel.queryUserAllOrder(userId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<List<UserOrderStatusGson>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<List<UserOrderStatusGson>> userOrderStatusGsonBaseGson) {
                        mMvpView.hideDialog();

                        mMvpView.loadUserMoreOrders(userOrderStatusGsonBaseGson.getData());

                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
