package com.example.kind.presenter;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.KindItemGson;
import com.example.commonlib.http.BaseObserver;
import com.example.kind.contract.KindContract;
import com.example.kind.model.KindModel;

import java.util.List;

public class KindPresenter extends BasePresenter<KindContract.View> implements KindContract.Presenter {
    private KindModel kindModel = new KindModel();
    private static final String TAG = "KindPresenter";
//private KindContract.View view;

    public KindPresenter(KindContract.View mMvpView) {
        super(mMvpView);
    }

//    public KindPresenter(KindContract.View view) {
//        this.view = view;
//    }

    @Override
    public void getGoodsList() {
        Log.i(TAG, "getGoodsList: ");
        kindModel.getGoodsList(new BaseObserver<KindItemGson>() {
            @Override
            protected void onHandleSuccess(List<KindItemGson> t) {
                if (t.size() > 0) {
                    Log.i(TAG, "onHandleSuccess: " + t.size());
                    mMvpView.setGoodsList(t);
                }

            }

            @Override
            protected void onHandleError(String msg) {

            }

            @Override
            public void onNext(BaseGson<KindItemGson> value) {
                super.onNext(value);
            }
        });
    }

    @Override
    public void getGoodsItemList(int pid) {
        kindModel.getGoodsListItem(new BaseObserver<KindItemGson>() {
            @Override
            protected void onHandleSuccess(List<KindItemGson> t) {

                    mMvpView.setGoodsItemList(t);


            }

            @Override
            protected void onHandleError(String msg) {

            }

            @Override
            public void onNext(BaseGson<KindItemGson> value) {
                super.onNext(value);
            }
        }, pid);
    }
}
