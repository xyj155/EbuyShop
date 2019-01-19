package com.example.commonlib.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.AddressOfNewReceiptContract;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.model.AddressOfNewReceiptModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddressOfNewReceiptPresenter extends BasePresenter<AddressOfNewReceiptContract.View> implements AddressOfNewReceiptContract.Presenter {
    public AddressOfNewReceiptPresenter(AddressOfNewReceiptContract.View mMvpView) {
        super(mMvpView);
    }

    private AddressOfNewReceiptModel addressOfNewReceiptModel = new AddressOfNewReceiptModel();

    @Override
    public void saveNewAddress(String username, String tel, String local, String detail, boolean isDefalt, String userId) {
        mMvpView.showDialog("");
        addressOfNewReceiptModel.saveNewAddress(username, tel, local, detail, isDefalt ? "1" : "0", userId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (emptyGsonBaseGson.isStatus()) {
                            mMvpView.saveNewAddressSuccess(true);
                        } else {
                            mMvpView.saveNewAddressSuccess(false);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
