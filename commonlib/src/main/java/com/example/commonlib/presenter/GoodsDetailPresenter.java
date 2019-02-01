package com.example.commonlib.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.gson.SubmitOrderGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.model.GoodsDetailModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodsDetailPresenter extends BasePresenter<GoodsDetailContract.View> implements GoodsDetailContract.Presenter {

    public GoodsDetailPresenter(GoodsDetailContract.View mMvpView) {
        super(mMvpView);
    }

    private GoodsDetailModel detailModel = new GoodsDetailModel();

    @Override
    public void setGoodsDetailById(String goodsId, String userId) {
        mMvpView.showDialog("加载中");
        detailModel.getGoodsDetailById(goodsId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodsDetailGson>>() {
                    @Override
                    public void onCompleted() {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onNext(BaseGson<GoodsDetailGson> goodsGsonBaseGson) {
                        mMvpView.loadGoodsDetail(goodsGsonBaseGson.getData().get(0));
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    @Override
    public void insertUserOrder(String userId, String goodsId) {
        mMvpView.showDialog("加载中");
        detailModel.insertUserOrder(userId, goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SubmitOrderGson>>() {
                    @Override
                    public void onCompleted() {
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onNext(BaseGson<SubmitOrderGson> goodsGsonBaseGson) {
                        if (goodsGsonBaseGson.isStatus())
                            mMvpView.insertUserOrder(goodsGsonBaseGson.getData().get(0));
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    @Override
    public void addUserCollection(String userId, String goodsId, String isDelete) {
        mMvpView.showDialog("");
        detailModel.addUserCollection(userId, goodsId, isDelete)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.addCollectionSuccess(emptyGsonBaseGson.isStatus());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
