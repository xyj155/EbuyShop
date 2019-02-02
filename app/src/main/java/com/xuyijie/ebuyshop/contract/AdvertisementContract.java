package com.xuyijie.ebuyshop.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.AdvertisementGson;

import rx.Observable;

public interface AdvertisementContract {
    interface Model {
        Observable<BaseGson<AdvertisementGson>> queryFlashAdvertisement();
    }

    interface View extends BaseView {
        void loadAdvertisement(AdvertisementGson advertisementGson);
        void loadEmpty();
    }

    interface Presenter {
        void queryFlashAdvertisement();
    }
}
