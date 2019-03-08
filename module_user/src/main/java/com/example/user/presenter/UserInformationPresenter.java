package com.example.user.presenter;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.BaseObserver;
import com.example.user.contract.UserInformationContract;
import com.example.user.model.UserInformationModel;

import okhttp3.MultipartBody;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserInformationPresenter extends BasePresenter<UserInformationContract.View> implements UserInformationContract.Presenter {
    private UserInformationModel userModel = new UserInformationModel();

    public UserInformationPresenter(UserInformationContract.View mMvpView) {
        super(mMvpView);
    }

    @Override
    public void updateUserAvatar(String userId, MultipartBody.Part avatar, String username) {
        userModel.updateUserAvatar(userId, avatar, username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        Log.i(TAG, "onNext: "+emptyGsonBaseGson);
                        if (emptyGsonBaseGson.isStatus()){
                            mMvpView.updateUserAvatar(emptyGsonBaseGson.isStatus(),emptyGsonBaseGson.getMsg());
                        }else {
                            mMvpView.updateUserAvatar(emptyGsonBaseGson.isStatus(),"");
                        }

                    }

                    @Override
                    public void onError(String error) {
                        Log.i(TAG, "onError: "+error);
                    }
                });
    }

    private static final String TAG = "UserInformationPresente";
}
