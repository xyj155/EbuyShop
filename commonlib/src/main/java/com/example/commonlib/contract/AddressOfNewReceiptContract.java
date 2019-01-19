package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;

import rx.Observable;

public interface AddressOfNewReceiptContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> saveNewAddress(String username, String tel, String local, String detail, String isDefalt,String userId);
    }

    interface View extends BaseView {
        void saveNewAddressSuccess(boolean success);
    }

    interface Presenter {
        void saveNewAddress(String username, String tel, String local, String detail, boolean isDefalt,String userId);
    }
}
