package com.example.goodscar.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;

import rx.Observable;

public interface GoodsOperationContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> addGoodsInShopCarById(String userId,String count, String goodsId,String type);
    }

    interface View  extends BaseView {
        void addGoodsInShopCar(boolean isSuccess);
    }

    interface Presenter {
        void addGoodsInShopCarById(String userId,String count, String goodsId,String type);
    }
}
