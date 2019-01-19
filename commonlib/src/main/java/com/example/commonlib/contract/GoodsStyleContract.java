package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.GoodsStyleGson;

import java.util.List;

import rx.Observable;

public interface GoodsStyleContract {
    interface Model {
        Observable<BaseGson<GoodsStyleGson>> queryGoodsStyle(String goodsId);
        Observable<BaseGson<EmptyGson>> addGoodsInShopCarById(String userId, String goodsId,String type);
    }

    interface View  extends BaseView {
        void loadGoodsStyle(List<GoodsStyleGson> goodsGson);
        void addGoodsInShopCar(boolean isSuccess);
    }

    interface Presenter {
        void queryGoodsStyle(String goodsId);
        void addGoodsInShopCarById(String userId, String goodsId,String type);
    }
}
