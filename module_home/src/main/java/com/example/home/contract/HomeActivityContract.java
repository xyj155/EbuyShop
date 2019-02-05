package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.GoodsGson;

import java.util.List;

import rx.Observable;

public interface HomeActivityContract {
    interface Model {
        Observable<BaseGson<GoodsGson>> queryHomeActivityByName(String activityName);

    }

    interface View extends BaseView {
        void queryHomeActivityByName(List<GoodsGson> list);
    }

    interface Presenter {
        void queryHomeActivityByName(String activityName);
    }
}
