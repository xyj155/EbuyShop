package com.example.module_login.presenter;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.http.BaseObserver;
import com.example.module_login.contract.UserContract;
import com.example.module_login.model.UserModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserPresenter extends BasePresenter<UserContract.View> implements UserContract.Presenter {
    private UserModel userModel = new UserModel();
    private static final String TAG = "UserPresenter";

    public UserPresenter(UserContract.View mMvpView) {
        super(mMvpView);
    }

    @Override
    public void userLogin(String username, String password) {
        mMvpView.showDialog("");
        userModel.userLoginByUserName(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserGson>>() {
                    @Override
                    public void onCompleted() {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onNext(BaseGson<UserGson> userGsonBaseGson) {
                        Log.i(TAG, "onNext: "+userGsonBaseGson.toString());
                        if (userGsonBaseGson.isStatus()) {
                            mMvpView.userLogin(userGsonBaseGson.getData().get(0));
                        } else {
                            mMvpView.showError(userGsonBaseGson.getMsg());
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                        Log.i(TAG, "onError: "+error);
                        mMvpView.showError("服务器错误，请重试！");
                    }
                });
    }

}
