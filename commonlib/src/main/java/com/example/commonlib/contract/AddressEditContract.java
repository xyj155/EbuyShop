package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;

import rx.Observable;

public interface AddressEditContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> updateAddress(String id,String username,String tel,String location,String detail);
    }

    interface View extends BaseView {
        void updateSuccess(boolean isSuccess);
    }

    interface Presenter {
        void updateAddress(String id,String username,String tel,String location,String detail);
    }
}
