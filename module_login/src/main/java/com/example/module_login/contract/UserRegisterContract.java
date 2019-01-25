package com.example.module_login.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;

import rx.Observable;

public interface UserRegisterContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> userRegister(String userId,
                                                     String count,
                                                     String goodsId,
                                                     String age,
                                                     String school);
    }

    interface View extends BaseView {
        void registerSuccess(EmptyGson emptyGson);
    }

    interface Presenter {
        void userRegister(String userId,
                          String count,
                          String goodsId,
                          String age,
                          String school);
    }
}
