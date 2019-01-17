package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.GoodsStyleGson;

import java.util.List;

import rx.Observable;

public interface GoodsStyleContract {
    interface Model {
        Observable<BaseGson<GoodsStyleGson>> queryGoodsStyle(String goodsId);
    }

    interface View  extends BaseView {
        void loadGoodsStyle(List<GoodsStyleGson> goodsGson);
    }

    interface Presenter {
        void queryGoodsStyle(String goodsId);
    }
}
