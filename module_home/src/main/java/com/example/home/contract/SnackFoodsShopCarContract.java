package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;

import rx.Observable;

public interface SnackFoodsShopCarContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> addSnackByUserId( String userId,  String goodsId,  String tasteId, String isDelete);

    }

    interface View  extends BaseView {
        void addSnackShopCar(boolean success);
    }

    interface Presenter {
        void addSnackByUserId( String userId,  String goodsId,  String tasteId, String isDelete);
    }
}
