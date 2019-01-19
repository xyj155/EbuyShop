package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.UserReceiveAddressGson;

import java.util.List;

import rx.Observable;

public interface UserReceivingAddressContract {
    interface Model {
        Observable<BaseGson<UserReceiveAddressGson>> queryUserReceiveAddress(String userId);
    }

    interface View  extends BaseView {
        void loadUserReceiveAddress(List<UserReceiveAddressGson> userReceiveAddressGsons);
    }

    interface Presenter {
        void queryUserReceiveAddress(String userId);
    }
}
