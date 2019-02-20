package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.SnackOrderDetailGson;

import rx.Observable;

public interface SnackOrderContract {
    interface Model {
        Observable<BaseGson<SnackOrderDetailGson>> querySnackFoodsOrder(String userId, String orderNum);
        Observable<BaseGson<EmptyGson>> submitUserSnackOrderByUserId(String userId, String orderNum,String token,String message,String addressId);
    }

    interface View extends BaseView {
        void loadUserSnackOrderList(SnackOrderDetailGson snackShopCarGsonList);
        void submitUserSnackOrderByUserId(boolean success);
    }

    interface Presenter {
        void querySnackFoodsOrder(String userId, String orderNum);
        void submitUserSnackOrderByUserId(String userId, String orderNum,String token,String message,String addressId);
    }
}
