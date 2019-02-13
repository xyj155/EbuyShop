package com.xuyijie.ebuyshop.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.AdvertisementGson;
import com.example.commonlib.gson.PopAdvertisementGson;

import java.util.List;

import rx.Observable;

public interface AdvertisementContract {
    interface Model {
        Observable<BaseGson<AdvertisementGson>> queryFlashAdvertisement();

        Observable<BaseGson<PopAdvertisementGson>> queryPopWindowAd();
    }

    interface View extends BaseView {
        void loadAdvertisement(AdvertisementGson advertisementGson);

        void loadEmpty();
        void queryPopWindowAd(List<PopAdvertisementGson> popAdvertisementGsons);
    }

    interface Presenter {
        void queryFlashAdvertisement();

        void queryPopWindowAd();
    }
}
