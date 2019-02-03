package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.NewestShelfGson;

import rx.Observable;

public interface NewUpperShelfContract {
    interface Model {
        Observable<BaseGson<NewestShelfGson>> newUpperShelf(String date);
    }

    interface View extends BaseView {
        void loadDateList(NewestShelfGson timeBeans);

    }

    interface Presenter {
        void newUpperShelf(String date);
    }
}
