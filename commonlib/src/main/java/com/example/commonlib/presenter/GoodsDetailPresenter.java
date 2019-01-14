package com.example.commonlib.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.gson.GoodsGson;
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
    public void setGoodsDetailById(String goodsId) {
        mMvpView.showDialog("加载中");
        detailModel.getGoodsDetailById(goodsId)
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
}
