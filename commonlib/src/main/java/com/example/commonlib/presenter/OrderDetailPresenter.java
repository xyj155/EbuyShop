package com.example.commonlib.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.OrderDetailContract;
import com.example.commonlib.gson.OrderDetailGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.model.OrderDetailModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderDetailPresenter extends BasePresenter<OrderDetailContract.View> implements OrderDetailContract.Presenter {
    public OrderDetailPresenter(OrderDetailContract.View mMvpView) {
        super(mMvpView);
    }

    private OrderDetailModel orderDetailModel = new OrderDetailModel();

    @Override
    public void confirmationOrderByUserId(String userId, String goodsId) {
        mMvpView.showDialog("");
        orderDetailModel.confirmationOrderByUserId(userId, goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<OrderDetailGson>>() {
                    @Override
                    public void onCompleted() {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onNext(BaseGson<OrderDetailGson> orderDetailGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.loadOrderDetil(orderDetailGsonBaseGson.getData().get(0));
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
