package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.GoodsGson;

import java.util.List;

import rx.Observable;

public interface MemberShipRightContract {
    interface Model {
        Observable<BaseGson<GoodsGson>> queryMemberShipGoods();
    }

    interface View extends BaseView {
        void queryMemberShipGoods(List<GoodsGson> goodsGsonList);
    }

    interface Presenter {
        void queryMemberShipGoods();
    }
}
