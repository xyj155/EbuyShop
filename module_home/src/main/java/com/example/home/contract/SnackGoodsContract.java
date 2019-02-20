package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.SnackGson;
import com.example.commonlib.gson.SnackKindGson;
import com.example.commonlib.gson.SnackShopCarGson;

import java.util.List;

import rx.Observable;

public interface SnackGoodsContract {
    interface Model {
        Observable<BaseGson<SnackKindGson>> queryAllSnack();

        Observable<BaseGson<SnackGson>> querySnackByKindId(String goodsKind, String userId);

        Observable<BaseGson<SnackShopCarGson>> queryUserAllSnack(String userId);

        Observable<BaseGson<EmptyGson>> submitSnackFoodsOrder(String userId, String token);
    }

    interface View extends BaseView {
        void queryAllSnack(List<SnackKindGson> snackKindGsons);

        void querySnackByKindId(List<SnackGson> list);

        void queryUserSnackShopCarAllSnack(List<SnackShopCarGson> list);
        void submitUserOrder(boolean list,String orderNum);
    }

    interface Presenter {
        void queryAllSnack();

        void querySnackByKindId(String goodsKind, String userId);

        void queryUserShopCarAllSnack(String userId);
        void submitSnackFoodsOrder(String userId, String token);
    }
}
