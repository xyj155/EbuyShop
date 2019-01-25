package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.gson.SubmitOrderGson;

import rx.Observable;

public interface GoodsDetailContract {
    interface Model {
        Observable<BaseGson<GoodsDetailGson>> getGoodsDetailById(String goodsId);

        Observable<BaseGson<SubmitOrderGson>> insertUserOrder(String userId, String goodsId);
    }

    interface View extends BaseView {
        void loadGoodsDetail(GoodsDetailGson goodsGson);
        void insertUserOrder(SubmitOrderGson goodsGson);
    }

    interface Presenter {
        void setGoodsDetailById(String goodsId);

        void insertUserOrder(String userId, String goodsId);
    }
}
