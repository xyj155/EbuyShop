package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.SnackOrderGson;

import java.util.List;

import rx.Observable;

public interface QuerySnackOrderContract {
    interface Model {
        Observable<BaseGson<SnackOrderGson>> queryUserSnackOrderListByUserId(String userId, String page, String limit);
    }

    interface View extends BaseView {
        void queryUserSnackOrderListByUserId(List<SnackOrderGson> list);

        void queryUserMoreSnackOrderListByUserId(List<SnackOrderGson> list);
    }

    interface Presenter {
        void queryUserSnackOrderListByUserId(String userId, String page, String limit);

        void queryUserMoreSnackOrderListByUserId(String userId, String page, String limit);
    }
}
