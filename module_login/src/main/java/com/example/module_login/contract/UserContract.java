package com.example.module_login.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.UserGson;

import rx.Observable;

public interface UserContract {
    interface Model {
        Observable<BaseGson<UserGson>> userLoginByUserName(String username, String password);
    }

    interface View extends BaseView {

        void userLogin(UserGson userGson);

    }

    interface Presenter {
        void userLogin(String username, String password);
    }
}
