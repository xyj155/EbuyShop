package com.example.module_login.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.http.BaseObserver;
import com.example.module_login.contract.UserRegisterContract;
import com.example.module_login.model.UserRegisterModel;

import okhttp3.MultipartBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserRegisterPresenter extends BasePresenter<UserRegisterContract.View> implements UserRegisterContract.Presenter {
    public UserRegisterPresenter(UserRegisterContract.View mMvpView) {
        super(mMvpView);
    }

    private UserRegisterModel registerModel = new UserRegisterModel();


    @Override
    public void userRegister(String username, String password, String telphone, String age,String sex, String school, MultipartBody.Part avatar) {
        mMvpView.showDialog("");
        registerModel.userRegister(username, password, telphone, age,sex, school, avatar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<UserGson> emptyGsonBaseGson) {
                        if (emptyGsonBaseGson.isStatus()){
                            mMvpView.registerSuccess(emptyGsonBaseGson.getData().get(0));
                        }else {
                            mMvpView.registerFailed();
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
