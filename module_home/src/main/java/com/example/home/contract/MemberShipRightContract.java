package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.CouponGson;
import com.example.commonlib.gson.GoodsGson;

import java.util.List;

import rx.Observable;

public interface MemberShipRightContract {
    interface Model {
        Observable<BaseGson<GoodsGson>> queryMemberShipGoods();
        Observable<BaseGson<CouponGson>> queryMemberShipCoupon(String userId);
    }

    interface View extends BaseView {
        void queryMemberShipGoods(List<GoodsGson> goodsGsonList);
        void queryMemberShipCoupon(List<CouponGson> couponGsonList);
    }

    interface Presenter {
        void queryMemberShipGoods();
        void queryMemberShipCoupon(String userId);
    }
}
