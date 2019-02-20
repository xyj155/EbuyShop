package com.example.home.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.GoodsSearchContract;
import com.example.home.model.GoodsSearchModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodsSeaechPresenter extends BasePresenter<GoodsSearchContract.View> implements GoodsSearchContract.Presenter {
    public GoodsSeaechPresenter(GoodsSearchContract.View mMvpView) {
        super(mMvpView);
    }

    private GoodsSearchModel searchModel = new GoodsSearchModel();

    @Override
    public void querySearchByLikeWord(String input) {
        mMvpView.showDialog("");
        searchModel.querySearchByLikeWord(input)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodsGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<GoodsGson> goodsGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.querySearchByLikeWord(goodsGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
