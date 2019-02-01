package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.gson.SubmitOrderGson;

import rx.Observable;

public interface GoodsDetailContract {
    interface Model {
        Observable<BaseGson<GoodsDetailGson>> getGoodsDetailById(String goodsId, String userId);

        Observable<BaseGson<SubmitOrderGson>> insertUserOrder(String userId, String goodsId);

        Observable<BaseGson<EmptyGson>> addUserCollection(String userId, String goodsId,String isDelete);
    }

    interface View extends BaseView {
        void loadGoodsDetail(GoodsDetailGson goodsGson);

        void insertUserOrder(SubmitOrderGson goodsGson);
        void addCollectionSuccess(boolean success);
    }

    interface Presenter {
        void setGoodsDetailById(String goodsId, String userId);

        void insertUserOrder(String userId, String goodsId);

        void addUserCollection(String userId, String goodsId,String isDelete);
    }
}
