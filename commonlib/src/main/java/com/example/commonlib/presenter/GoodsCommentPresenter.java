package com.example.commonlib.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.GoodsCommentContract;
import com.example.commonlib.gson.GoodsCommentGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.model.GoodsCommentModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodsCommentPresenter extends BasePresenter<GoodsCommentContract.View> implements GoodsCommentContract.Presenter {

    public GoodsCommentPresenter(GoodsCommentContract.View mMvpView) {
        super(mMvpView);
    }

    private GoodsCommentModel goodsCommentModel = new GoodsCommentModel();

    @Override
    public void queryGoodsComment(String goodsId) {
        mMvpView.showDialog("");
        goodsCommentModel.queryGoodsComment(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodsCommentGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<GoodsCommentGson> goodsCommentGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.setGoodsComment(goodsCommentGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
