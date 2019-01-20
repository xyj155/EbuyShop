package com.example.user.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.CouponGson;

import java.util.List;

import rx.Observable;

public interface UserCouponContract {
    interface Model {
        Observable<BaseGson<CouponGson>> queryUserCouponList(String userId);
    }

    interface View  extends BaseView {
        void loadCouponList(List<CouponGson> couponGsons);
    }

    interface Presenter {
        void queryUserCouponList(String userId);
    }
}
