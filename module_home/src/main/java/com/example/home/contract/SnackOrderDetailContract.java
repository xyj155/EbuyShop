package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.SnackOrderDetailGson;

import rx.Observable;

public interface SnackOrderDetailContract {
    interface Model {
        Observable<BaseGson<SnackOrderDetailGson>> querySnackOrderDetail(String userId, String orderNum);
    }

    interface View extends BaseView {
        void loadSnackGoodsDetail(SnackOrderDetailGson snackOrderDetailGson);
    }

    interface Presenter {
        void querySnackOrderDetail(String userId, String orderNum);
    }
}
