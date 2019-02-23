package com.example.home.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.SnackOrderGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.QuerySnackOrderContract;
import com.example.home.model.QuerySnackOrderModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QuerySnackOrderPresenter extends BasePresenter<QuerySnackOrderContract.View> implements QuerySnackOrderContract.Presenter {
    public QuerySnackOrderPresenter(QuerySnackOrderContract.View mMvpView) {
        super(mMvpView);
    }

    private QuerySnackOrderModel querySnackOrderModel = new QuerySnackOrderModel();

    @Override
    public void queryUserSnackOrderListByUserId(String userId, String page, String limit) {
        mMvpView.showDialog("");
        querySnackOrderModel.queryUserSnackOrderListByUserId(userId, page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SnackOrderGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SnackOrderGson> snackOrderGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.queryUserSnackOrderListByUserId(snackOrderGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    @Override
    public void queryUserMoreSnackOrderListByUserId(String userId, String page, String limit) {
        mMvpView.showDialog("");
        querySnackOrderModel.queryUserSnackOrderListByUserId(userId, page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SnackOrderGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SnackOrderGson> snackOrderGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.queryUserMoreSnackOrderListByUserId(snackOrderGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
