package com.example.module_login.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.UserGson;

import rx.Observable;

public interface UserContract {
    interface Model {
        Observable<BaseGson<UserGson>> userLoginByUserName(String username, String password);

        Observable<BaseGson<EmptyGson>> queryUserAccount(String username);
    }

    interface View extends BaseView {

        void userLogin(UserGson userGson);

        void isHaving(boolean isHaving);
    }

    interface Presenter {
        void userLogin(String username, String password);

        void queryUserAccount(String username);
    }
}
