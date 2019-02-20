package com.example.commonlib.presenter;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.UserMemberDateContract;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.model.UserMemberDateModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserMemberDatePresenter extends BasePresenter<UserMemberDateContract.View> implements UserMemberDateContract.Presenter {
    private UserMemberDateModel userMemberDateModel = new UserMemberDateModel();

    public UserMemberDatePresenter(UserMemberDateContract.View mMvpView) {
        super(mMvpView);
    }

    @Override
    public void judgementMember(String userId) {
        userMemberDateModel.judgementMember(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        Log.i(TAG, "onNext: "+emptyGsonBaseGson);
                        mMvpView.loadUserMember(Integer.parseInt(emptyGsonBaseGson.getCode()));
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private static final String TAG = "UserMemberDatePresenter";
}
