package com.example.user.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.UserOrderStatusGson;

import java.util.List;

import rx.Observable;

public interface UserAllOrderContract {
    interface Model {
        Observable<BaseGson<List<UserOrderStatusGson> >> queryUserAllOrder(String userId, String page);
    }

    interface View extends BaseView {
        void loadUserAllOrders(List<List<UserOrderStatusGson>>  userOrderStatusGsons);
        void loadUserMoreOrders(List<List<UserOrderStatusGson>>  userOrderStatusGsons);
    }

    interface Presenter {
        void queryUserAllOrder(String userId, String page);
        void loadUserMoreOrders(String userId, String page);
    }
}
