package com.example.user.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;

import rx.Observable;

public interface UserOrderDeleteContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> deleteOrderByOrderNum(String orderNum, String userId);
    }

    interface View extends BaseView {
        void deleteSuccess(boolean isDelete);
    }

    interface Presenter {
        void deleteOrderByOrderNum(String orderNum, String userId);
    }
}
