package com.example.user.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.BaseObserver;
import com.example.user.contract.UpdateOrderStatusContract;
import com.example.user.model.UpdateOrderStatusModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UpdateOrderStatusPresenter extends BasePresenter<UpdateOrderStatusContract.View> implements UpdateOrderStatusContract.Presenter {

    public UpdateOrderStatusPresenter(UpdateOrderStatusContract.View mMvpView) {
        super(mMvpView);
    }

    private UpdateOrderStatusModel userOrderStatusModel = new UpdateOrderStatusModel();

    @Override
    public void updateOrderStatusByReceive(String userId, String orderNum) {
        mMvpView.showDialog("");
        userOrderStatusModel.updateOrderStatusByReceive(userId, orderNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (emptyGsonBaseGson.isStatus()) {
                            mMvpView.updateStatus(true);
                        } else {
                            mMvpView.updateStatus(false);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
