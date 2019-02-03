package com.example.home.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.NewestShelfGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.NewUpperShelfContract;
import com.example.home.model.NewUpperShelfModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewUpperShelfPresenter extends BasePresenter<NewUpperShelfContract.View> implements NewUpperShelfContract.Presenter {
    public NewUpperShelfPresenter(NewUpperShelfContract.View mMvpView) {
        super(mMvpView);
    }

    private NewUpperShelfModel shelfModel = new NewUpperShelfModel();

    @Override
    public void newUpperShelf(String date) {
        mMvpView.showDialog("");
        shelfModel.newUpperShelf(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<NewestShelfGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<NewestShelfGson> newestShelfGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.loadDateList(newestShelfGsonBaseGson.getSingleData());

                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
