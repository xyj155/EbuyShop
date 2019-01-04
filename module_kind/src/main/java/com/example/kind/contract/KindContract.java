package com.example.kind.contract;

import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.KindItemGson;
import com.example.commonlib.http.BaseObserver;

import java.util.List;

public interface KindContract {
    interface Model {
        void getGoodsList(BaseObserver observer);
        void getGoodsListItem(BaseObserver observer,int pid);
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
