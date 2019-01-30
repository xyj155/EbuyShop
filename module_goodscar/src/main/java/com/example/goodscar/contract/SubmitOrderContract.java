package com.example.goodscar.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.SubmitOrderItemGson;

import rx.Observable;

public interface SubmitOrderContract {
    interface Model {
        Observable<BaseGson<SubmitOrderItemGson>> submitUserOrder(String userId, String goodsId);
    }

    interface View  extends BaseView {
        void submitUserOrder(boolean submit,String goodsOrderNum);
    }

    interface Presenter {
        void submitUserOrder(String userId,String goodsId);
    }
}