package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.BannerGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.HomePurseAdvertisementGson;
import com.example.commonlib.gson.HotPurseActivityGson;
import com.example.commonlib.gson.MarQueenGson;
import com.example.commonlib.gson.TimeGoodsGson;

import java.util.List;

import rx.Observable;

public interface HomePageContract {
    interface Model {
        Observable<BaseGson<GoodsGson>> getTimerGoodsList(String type);

        Observable<BaseGson<GoodsGson>> getPurseGoodsList(String type);

        Observable<BaseGson<HotPurseActivityGson>> getHomeActivity();

        Observable<BaseGson<BannerGson>> getHomeBanner();

        Observable<BaseGson<MarQueenGson>> getMarqueenList();
        Observable<BaseGson<TimeGoodsGson.TimeBean>> queryTimeSell();
        Observable<BaseGson<HomePurseAdvertisementGson>> queryHomePurseGoodsAdvertisement();
    }

    interface View extends BaseView {
        void loadTimeGoodsList(List<GoodsGson> userGson);

        void loadPurseGoodsList(List<GoodsGson> userGson);

        void loadHomeActivity(List<HotPurseActivityGson> userGson);

        void loadHomeBanner(List<BannerGson> userGson);

        void loadMarqueenList(List<MarQueenGson> list);

        void loadTimer(TimeGoodsGson.TimeBean timeBean);

        void  queryHomePurseGoodsAdvertisement(HomePurseAdvertisementGson homePurseAdvertisement);
    }

    interface Presenter {
        void setPurseGoodsList(String type1, String type2);
        void  queryTimeSell();

    }
}
