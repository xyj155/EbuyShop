package com.example.goodscar.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.ShopCarGson;

import java.util.List;

import rx.Observable;

public interface ShopCarContract {
    interface Model {
        Observable<BaseGson<ShopCarGson>> queryUserShopCarByUid(String userId);
    }

    interface View extends BaseView {
        void loadUserShopCar(List<ShopCarGson> shopCarGsons);
    }

    interface Presenter {
        void queryUserShopCarByUid(String userId,boolean isflash);
    }
}
