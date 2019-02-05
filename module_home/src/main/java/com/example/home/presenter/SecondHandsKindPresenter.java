package com.example.home.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.SecondKindGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.SecondHandsKindContract;
import com.example.home.model.SecondHandsKindModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SecondHandsKindPresenter extends BasePresenter<SecondHandsKindContract.View> implements SecondHandsKindContract.Presenter {
    public SecondHandsKindPresenter(SecondHandsKindContract.View mMvpView) {
        super(mMvpView);
    }

    private SecondHandsKindModel secondHandsKindModel = new SecondHandsKindModel();

    @Override
    public void queryAllSecondKind() {
        mMvpView.showDialog("");
        secondHandsKindModel.queryAllSecondKind()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SecondKindGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SecondKindGson> secondKindGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (secondKindGsonBaseGson.isStatus()) {
                            mMvpView.setSecondKind(secondKindGsonBaseGson.getData());
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
