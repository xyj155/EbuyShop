package com.example.user.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.GoodsGson;

import java.util.List;

import rx.Observable;

public interface UserCollectionContract {
    interface Model {
        Observable<BaseGson<GoodsGson>> queryUserCollection(String userId);
    }

    interface View extends BaseView {
        void setUserCollection(List<GoodsGson> goodsGsons);
    }

    interface Presenter {
        void queryUserCollection(String userId);
    }
}
