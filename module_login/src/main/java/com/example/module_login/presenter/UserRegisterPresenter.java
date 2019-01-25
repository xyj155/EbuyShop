package com.example.module_login.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.BaseObserver;
import com.example.module_login.contract.UserRegisterContract;
import com.example.module_login.model.UserRegisterModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserRegisterPresenter extends BasePresenter<UserRegisterContract.View> implements UserRegisterContract.Presenter {
    public UserRegisterPresenter(UserRegisterContract.View mMvpView) {
        super(mMvpView);
    }

    private UserRegisterModel registerModel = new UserRegisterModel();

    @Override
    public void userRegister(String userId, String count, String goodsId, String age, String school) {
        registerModel.userRegister(userId, count, goodsId, age, school)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        mMvpView.registerSuccess(emptyGsonBaseGson.getData().get(0));
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
