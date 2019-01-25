package com.example.goodscar.presenter;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.BaseObserver;
import com.example.goodscar.contract.GoodsOperationContract;
import com.example.goodscar.model.GoodsCarOperationModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodsOperationPresenter extends BasePresenter<GoodsOperationContract.View> implements GoodsOperationContract.Presenter {

    public GoodsOperationPresenter(GoodsOperationContract.View mMvpView) {
        super(mMvpView);
    }

    private GoodsCarOperationModel styleModel = new GoodsCarOperationModel();


    @Override
    public void addGoodsInShopCarById(String userId,String count, final String goodsId,String type) {
        mMvpView.showDialog("");
        styleModel.addGoodsInShopCarById(userId, count,goodsId,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> goodsStyleGsonBaseGson) {
//                        if (goodsStyleGsonBaseGson.isStatus()) {
//                            mMvpView.addGoodsInShopCar(true);
//                        } else {
//                            mMvpView.addGoodsInShopCar(false);
//                        }
                        Log.i(TAG, "onNext: "+goodsStyleGsonBaseGson.toString());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    private static final String TAG = "GoodsOperationPresenter";}
