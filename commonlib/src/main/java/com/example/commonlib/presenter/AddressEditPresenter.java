package com.example.commonlib.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.contract.AddressEditContract;
import com.example.commonlib.model.AddressEditModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddressEditPresenter extends BasePresenter<AddressEditContract.View> implements AddressEditContract.Presenter {

    public AddressEditPresenter(AddressEditContract.View mMvpView) {
        super(mMvpView);
    }

    private AddressEditModel addressEditModel = new AddressEditModel();

    @Override
    public void updateAddress(String id, String username, String tel, String location, String detail) {
        mMvpView.showDialog("");
        addressEditModel.updateAddress(id, username, tel, location, detail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        mMvpView.updateSuccess(emptyGsonBaseGson.isStatus());
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
