package com.example.user.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.UserOrderStatusGson;

import java.util.List;

import rx.Observable;

public interface UserFormStatusContract {
    interface Model {
         Observable<BaseGson<List<UserOrderStatusGson>>> queryUserOrderByStatus(String userId, String status);
    }

    interface View extends BaseView {
        void loadUserOrderByStatus(List<List<UserOrderStatusGson>> userOrderStatusGsons);
    }

    interface Presenter {
        void queryUserOrderByStatus(String userId, String status);
    }
}
