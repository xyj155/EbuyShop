package com.example.user.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.ExpressTraceGson;
import com.example.commonlib.http.BaseObserver;
import com.example.user.contract.ExpressTraceContract;
import com.example.user.model.ExpressTraceModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ExpressTracePresenter extends BasePresenter<ExpressTraceContract.View> implements ExpressTraceContract.Presenter {
    public ExpressTracePresenter(ExpressTraceContract.View mMvpView) {
        super(mMvpView);
    }

    private ExpressTraceModel expressTraceModel = new ExpressTraceModel();

    @Override
    public void queryExpressByNum(String expressNum) {
        mMvpView.showDialog("");
        expressTraceModel.queryExpressByNum(expressNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<ExpressTraceGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<ExpressTraceGson> expressTraceGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.loadTrace(expressTraceGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
