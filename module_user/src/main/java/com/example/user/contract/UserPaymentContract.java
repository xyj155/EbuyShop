package com.example.user.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.UserPaymentGson;

import rx.Observable;

public interface UserPaymentContract {
    interface Model {
        Observable<BaseGson<UserPaymentGson>> queryUserOrderCount(String userId);
    }

    interface View extends BaseView {
       void setUserOrderCount(UserPaymentGson userOrderCount);
    }

    interface Presenter {
        void queryUserOrderCount(String userId);
    }
}
