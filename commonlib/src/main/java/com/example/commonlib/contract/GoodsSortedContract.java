package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.GoodsGson;

import java.util.List;

import rx.Observable;

public interface GoodsSortedContract {
    interface Model {
        Observable<BaseGson<GoodsGson>> getGoodsListByKind(String kind,String type,String isacs);
    }

    interface View extends BaseView {
        void setGoodsListByKind(List<GoodsGson> listByKind);
    }

    interface Presenter {
        void getGoodsListByKind(String kind,String type,String isacs);
    }
}
