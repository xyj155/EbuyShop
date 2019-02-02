package com.example.user.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;

import rx.Observable;

public interface UpdateOrderStatusContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> updateOrderStatusByReceive(String userId,String orderNum);
    }

    interface View extends BaseView {
        void updateStatus(boolean success);
    }

    interface Presenter {
        void updateOrderStatusByReceive(String userId,String orderNum);
    }
}
