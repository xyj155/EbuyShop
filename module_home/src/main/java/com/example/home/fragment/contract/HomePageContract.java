package com.example.home.fragment.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.HotPurseActivityGson;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.http.BaseObserver;

import java.util.List;

import rx.Observable;

public interface HomePageContract {
    interface Model {
        Observable<BaseGson<GoodsGson>> getTimerGoodsList(String type);
        Observable<BaseGson<GoodsGson>> getPurseGoodsList( String type);
        Observable<BaseGson<HotPurseActivityGson>> getHomeActivity();
    }

    interface View extends BaseView {
        void loadTimeGoodsList(List<GoodsGson> userGson);
        void loadPurseGoodsList(List<GoodsGson> userGson);
        void loadHomeActivity(List<HotPurseActivityGson> userGson);
    }

    interface Presenter  {
        void setPurseGoodsList(String type1,String type2);

    }
}
