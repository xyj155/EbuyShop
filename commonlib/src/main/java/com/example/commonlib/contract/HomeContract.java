package com.example.commonlib.contract;

import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.http.BaseObserver;

import java.util.List;

public interface HomeContract {
    interface Model {
        void userLogin(BaseObserver observer);
    }

    interface View extends BaseView {
        void loadUser(List<UserGson> userGson);
    }

    interface Presenter {
        void userLogin();
    }
}
