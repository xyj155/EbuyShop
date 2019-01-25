package com.example.goodscar.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.SubmitOrderItemGson;
import com.example.commonlib.http.BaseObserver;
import com.example.goodscar.contract.SubmitOrderContract;
import com.example.goodscar.model.SubmitOrderModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SubmitOrderPresenter extends BasePresenter<SubmitOrderContract.View> implements SubmitOrderContract.Presenter {

    public SubmitOrderPresenter(SubmitOrderContract.View mMvpView) {
        super(mMvpView);
    }

    private SubmitOrderModel submitOrderModel = new SubmitOrderModel();

    @Override
    public void submitUserOrder(String userId, String goodsId) {
        mMvpView.showDialog("");
        submitOrderModel.submitUserOrder(userId, goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SubmitOrderItemGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SubmitOrderItemGson> emptyGsonBaseGson) {
                        if (emptyGsonBaseGson.isStatus()) {
                            mMvpView.submitUserOrder(true,emptyGsonBaseGson.getData().get(0).getOrderNum());
                        } else {
                            mMvpView.submitUserOrder(false,emptyGsonBaseGson.getData().get(0).getOrderNum());
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
