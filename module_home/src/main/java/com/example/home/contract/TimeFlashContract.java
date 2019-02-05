package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.TimeGoodsGson;

import rx.Observable;

public interface TimeFlashContract {
    interface Model {
        Observable<BaseGson<TimeGoodsGson>> queryGoodsTime(String timeId);
    }

    interface View extends BaseView {
        void queryTimeGoods(TimeGoodsGson goodsGsons);
    }

    interface Presenter {
        void queryGoodsTime(String timeId);
    }
}
