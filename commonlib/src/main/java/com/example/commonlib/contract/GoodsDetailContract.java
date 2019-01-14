package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.gson.GoodsGson;

import rx.Observable;

public interface GoodsDetailContract {
    interface Model {
        Observable<BaseGson<GoodsDetailGson>> getGoodsDetailById(String goodsId);
    }

    interface View extends BaseView {
        void loadGoodsDetail(GoodsDetailGson goodsGson);
    }

    interface Presenter {
        void setGoodsDetailById(String goodsId);
    }
}
