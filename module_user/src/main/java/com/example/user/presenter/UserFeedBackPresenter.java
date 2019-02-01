package com.example.user.presenter;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.BaseObserver;
import com.example.user.contract.UserFeedBackContract;
import com.example.user.model.UserFeedBackModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserFeedBackPresenter extends BasePresenter<UserFeedBackContract.View> implements UserFeedBackContract.Presenter {

    public UserFeedBackPresenter(UserFeedBackContract.View mMvpView) {
        super(mMvpView);
    }

    private UserFeedBackModel feedBackModel = new UserFeedBackModel();

    @Override
    public void userFeedBack(Map<String, RequestBody> partMap, List<MultipartBody.Part> file) {
        mMvpView.showDialog("");
        feedBackModel.userFeedBack(partMap, file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.uploadSuccess(emptyGsonBaseGson.isStatus());
                    }

                    @Override
                    public void onError(String error) {
                        Log.i(TAG, "onError: "+error);
                        mMvpView.hideDialog();
                    }
                });

    }

    private static final String TAG = "UserFeedBackPresenter";

}
