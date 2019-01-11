package com.example.commonlib.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.GoodsSortedContract;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.model.GoodsSortedModel;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodsSortedPresenter extends BasePresenter<GoodsSortedContract.View> implements GoodsSortedContract.Presenter {
    private GoodsSortedModel sortedModel = new GoodsSortedModel();

    public GoodsSortedPresenter(GoodsSortedContract.View mMvpView) {
        super(mMvpView);
    }

    @Override
    public void getGoodsListByKind(String kind,String type,String isasc) {
        mMvpView.showDialog("加载中");
        sortedModel.getGoodsListByKind(kind,type,isasc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseGson<GoodsGson>>() {

                    @Override
                    public void onCompleted() {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onNext(BaseGson<GoodsGson> value) {
                        mMvpView.setGoodsListByKind(value.getData());
                        mMvpView.hideDialog();
                    }
                });

    }
}
