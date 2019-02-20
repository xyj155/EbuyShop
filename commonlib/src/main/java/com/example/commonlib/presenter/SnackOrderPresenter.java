package com.example.commonlib.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.SnackOrderContract;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.SnackOrderDetailGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.model.SnackOrderModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SnackOrderPresenter extends BasePresenter<SnackOrderContract.View> implements SnackOrderContract.Presenter {

    public SnackOrderPresenter(SnackOrderContract.View mMvpView) {
        super(mMvpView);
    }

    private SnackOrderModel snaackOrderModel = new SnackOrderModel();


    @Override
    public void querySnackFoodsOrder(String userId, String orderNum) {
        mMvpView.showDialog("");
        snaackOrderModel.querySnackFoodsOrder(userId, orderNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SnackOrderDetailGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SnackOrderDetailGson> snackShopCarGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (snackShopCarGsonBaseGson.isStatus()) {
                            mMvpView.loadUserSnackOrderList(snackShopCarGsonBaseGson.getSingleData());
                        } else {
                            mMvpView.showError("获取订单出错！");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    @Override
    public void submitUserSnackOrderByUserId(String userId, String orderNum, String token, String message, String addressId) {
        mMvpView.showDialog("");
        snaackOrderModel.submitUserSnackOrderByUserId(userId, orderNum, token, message, addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> snackShopCarGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.submitUserSnackOrderByUserId(snackShopCarGsonBaseGson.isStatus());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
