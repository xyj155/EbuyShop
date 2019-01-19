package com.example.commonlib.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.GoodsStyleContract;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.GoodsStyleGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.model.GoodsStyleModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodsStylePresenter extends BasePresenter<GoodsStyleContract.View> implements GoodsStyleContract.Presenter {

    public GoodsStylePresenter(GoodsStyleContract.View mMvpView) {
        super(mMvpView);
    }

    private GoodsStyleModel styleModel = new GoodsStyleModel();

    @Override
    public void queryGoodsStyle(String goodsId) {
        mMvpView.showDialog("");
        styleModel.queryGoodsStyle(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodsStyleGson>>() {
                    @Override
                    public void onCompleted() {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onNext(BaseGson<GoodsStyleGson> goodsStyleGsonBaseGson) {
                        mMvpView.loadGoodsStyle(goodsStyleGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    @Override
    public void addGoodsInShopCarById(String userId, final String goodsId, String type) {
        mMvpView.showDialog("");
        styleModel.addGoodsInShopCarById(userId, goodsId, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> goodsStyleGsonBaseGson) {
                        if (goodsStyleGsonBaseGson.isStatus()) {
                            mMvpView.addGoodsInShopCar(true);
                        } else {
                            mMvpView.addGoodsInShopCar(false);
                        }

                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
