package com.example.user.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.BaseObserver;
import com.example.user.contract.UserOrderDeleteContract;
import com.example.user.model.UserOrderDeleteModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserOrderDeletePresenter extends BasePresenter<UserOrderDeleteContract.View> implements UserOrderDeleteContract.Presenter {

    public UserOrderDeletePresenter(UserOrderDeleteContract.View mMvpView) {
        super(mMvpView);
    }

    private UserOrderDeleteModel orderDeleteModel = new UserOrderDeleteModel();

    @Override
    public void deleteOrderByOrderNum(String orderNum, String userId) {
        mMvpView.showDialog("");
        orderDeleteModel.deleteOrderByOrderNum(orderNum, userId)
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
                            mMvpView.deleteSuccess(true);
                        } else {
                            mMvpView.deleteSuccess(false);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
