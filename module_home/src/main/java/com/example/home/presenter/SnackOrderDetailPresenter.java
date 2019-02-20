package com.example.home.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.SnackOrderDetailGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.SnackOrderDetailContract;
import com.example.home.model.SnackOrderDetailModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SnackOrderDetailPresenter extends BasePresenter<SnackOrderDetailContract.View> implements SnackOrderDetailContract.Presenter {
    private SnackOrderDetailModel snackOrderDetailModel = new SnackOrderDetailModel();

    public SnackOrderDetailPresenter(SnackOrderDetailContract.View mMvpView) {
        super(mMvpView);
    }

    @Override
    public void querySnackOrderDetail(String userId, String orderNum) {
        mMvpView.showDialog("");
        snackOrderDetailModel.querySnackOrderDetail(userId, orderNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SnackOrderDetailGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SnackOrderDetailGson> orderDetailGsonBaseGson) {
                        if (orderDetailGsonBaseGson.isStatus()){
                            mMvpView.loadSnackGoodsDetail(orderDetailGsonBaseGson.getSingleData());
                        }else {
                            mMvpView.showError("获取订单出错");
                        }
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
