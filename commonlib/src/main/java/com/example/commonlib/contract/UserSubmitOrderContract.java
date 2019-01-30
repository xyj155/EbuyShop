package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;

import rx.Observable;

public interface UserSubmitOrderContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> submitOrderByUserId( String userId,String address,  String goodsId,  String couponId,  String orderNum, String userToken,String message,String expressId);
    }

    interface View extends BaseView {
        void submitStatus(boolean success);

    }

    interface Presenter {
        void submitOrderByUserId( String userId,  String goodsId, String address, String couponId,  String orderNum, String userToken,String message,String expressId);
    }
}
