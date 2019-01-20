package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.OrderDetailGson;

import rx.Observable;


public interface OrderDetailContract {
    interface Model {
        Observable<BaseGson<OrderDetailGson>> confirmationOrderByUserId(String userId, String goodsId);
    }

    interface View extends BaseView {
        void loadOrderDetil(OrderDetailGson orderDetailGson);
    }

    interface Presenter {
        void confirmationOrderByUserId(String userId, String goodsId);
    }
}
