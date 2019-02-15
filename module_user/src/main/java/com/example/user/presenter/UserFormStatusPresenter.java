package com.example.user.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.UserOrderStatusGson;
import com.example.commonlib.http.BaseObserver;
import com.example.user.contract.UserFormStatusContract;
import com.example.user.model.UserFormStatusModel;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserFormStatusPresenter extends BasePresenter<UserFormStatusContract.View> implements UserFormStatusContract.Presenter {
    public UserFormStatusPresenter(UserFormStatusContract.View mMvpView) {
        super(mMvpView);
    }

    private UserFormStatusModel userFormStatusModel = new UserFormStatusModel();

    @Override
    public void queryUserOrderByStatus(String userId, String status) {
        mMvpView.showDialog("");
        userFormStatusModel.queryUserOrderByStatus(userId, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<List<UserOrderStatusGson>>>() {
                    @Override
                    public void onCompleted() {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onNext(BaseGson<List<UserOrderStatusGson>> userOrderStatusGsonBaseGson) {
//                        if (userOrderStatusGsonBaseGson.getData().size() > 0) {
                        mMvpView.loadUserOrderByStatus(userOrderStatusGsonBaseGson.getData());
//                        }
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
