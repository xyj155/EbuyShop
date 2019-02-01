package com.example.user.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.http.BaseObserver;
import com.example.user.contract.UserCollectionContract;
import com.example.user.model.UserCollectionModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserCollectionPresenter extends BasePresenter<UserCollectionContract.View> implements UserCollectionContract.Presenter {
    public UserCollectionPresenter(UserCollectionContract.View mMvpView) {
        super(mMvpView);
    }

    private UserCollectionModel userCollectionModel = new UserCollectionModel();

    @Override
    public void queryUserCollection(String userId) {
        mMvpView.showDialog("");
        userCollectionModel.queryUserCollection(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodsGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<GoodsGson> goodsGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.setUserCollection(goodsGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
