package com.example.kind.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.KindItemGson;
import com.example.commonlib.http.BaseObserver;

import java.util.List;

import rx.Observable;

public interface KindContract {
    interface Model {
        Observable<BaseGson<KindItemGson>> getGoodsList();
        Observable<BaseGson<KindItemGson>>  getGoodsListItem( int pid);
    }

    interface View extends BaseView {
        void setGoodsList(List<KindItemGson> userGson);
        void setGoodsItemList(List<KindItemGson> userGson);
    }

    interface Presenter  {
        void getGoodsList();
        void getGoodsItemList(int pid);
    }
}
