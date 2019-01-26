package com.example.module_login.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.UserGson;

import okhttp3.MultipartBody;
import rx.Observable;

public interface UserRegisterContract {
    interface Model {
        Observable<BaseGson<UserGson>> userRegister(String username,
                                                     String password,
                                                     String telphone,
                                                     String age,
                                                     String sex,
                                                     String school,
                                                     MultipartBody.Part avatar);
    }

    interface View extends BaseView {
        void registerSuccess(UserGson emptyGson);
        void registerFailed();
    }

    interface Presenter {
        void userRegister(String username,
                          String password,
                          String telphone,
                          String age,
                          String sex,
                          String school,
                          MultipartBody.Part avatar);
    }
}
