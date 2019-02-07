package com.example.home.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.MemberGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.MembershipOpeningContract;
import com.example.home.model.MembershipOpeningModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MembershipOpeningPresenter extends BasePresenter<MembershipOpeningContract.View> implements MembershipOpeningContract.Presenter {

    public MembershipOpeningPresenter(MembershipOpeningContract.View mMvpView) {
        super(mMvpView);
    }

    private MembershipOpeningModel membershipOpeningModel = new MembershipOpeningModel();

    @Override
    public void queryMemberPrice() {
        mMvpView.showDialog("");
        membershipOpeningModel.queryMemberPrice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<MemberGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<MemberGson> memberGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (memberGsonBaseGson.getData() != null) {
                            mMvpView.queryMemberPrice(memberGsonBaseGson.getData());
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    @Override
    public void submitUserMemberShip(String userId, String memberRank) {
        mMvpView.showDialog("");
        membershipOpeningModel.submitUserMemberShip(userId, memberRank)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (emptyGsonBaseGson.isStatus()) {
                            mMvpView.submitUserMember(true);
                        } else {
                            mMvpView.submitUserMember(false);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
