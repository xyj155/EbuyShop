package com.example.commonlib.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.ExpressContract;
import com.example.commonlib.gson.ExpressGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.model.ExpressModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ExpressPresenter extends BasePresenter<ExpressContract.View> implements ExpressContract.Presenter {

    public ExpressPresenter(ExpressContract.View mMvpView) {
        super(mMvpView);
    }

    private ExpressModel expressModel = new ExpressModel();

    @Override
    public void queryExpress() {
        expressModel.queryExpress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<ExpressGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<ExpressGson> expressGsonBaseGson) {
                        mMvpView.loadExpress(expressGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
