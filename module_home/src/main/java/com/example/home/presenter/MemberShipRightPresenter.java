package com.example.home.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.CouponGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.MemberShipRightContract;
import com.example.home.model.MemberShipRightModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MemberShipRightPresenter extends BasePresenter<MemberShipRightContract.View> implements MemberShipRightContract.Presenter {

    public MemberShipRightPresenter(MemberShipRightContract.View mMvpView) {
        super(mMvpView);
    }

    private MemberShipRightModel memberShipRightModel = new MemberShipRightModel();

    @Override
    public void queryMemberShipGoods() {
        mMvpView.showDialog("");
        memberShipRightModel.queryMemberShipGoods()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodsGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<GoodsGson> goodsGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (goodsGsonBaseGson.isStatus()) {
                            mMvpView.queryMemberShipGoods(goodsGsonBaseGson.getData());
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    @Override
    public void queryMemberShipCoupon(String userId) {
        mMvpView.showDialog("");
        memberShipRightModel.queryMemberShipCoupon(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<CouponGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<CouponGson> goodsGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (goodsGsonBaseGson.isStatus()) {
                            mMvpView.queryMemberShipCoupon(goodsGsonBaseGson.getData());
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
