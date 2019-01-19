package com.example.goodscar.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.ShopCarGson;
import com.example.commonlib.http.BaseObserver;
import com.example.goodscar.contract.ShopCarContract;
import com.example.goodscar.model.ShopCarModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShopCarPresenter extends BasePresenter<ShopCarContract.View> implements ShopCarContract.Presenter {
    public ShopCarPresenter(ShopCarContract.View mMvpView) {
        super(mMvpView);
    }

    private ShopCarModel shopCarModel = new ShopCarModel();

    @Override
    public void queryUserShopCarByUid(String userId) {
        mMvpView.showDialog("");
        shopCarModel.queryUserShopCarByUid(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<ShopCarGson>>() {
                    @Override
                    public void onCompleted() {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onNext(BaseGson<ShopCarGson> shopCarGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.loadUserShopCar(shopCarGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
