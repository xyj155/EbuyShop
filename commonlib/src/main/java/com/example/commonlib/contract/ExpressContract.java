package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.ExpressGson;

import java.util.List;

import rx.Observable;

public interface ExpressContract {
    interface Model {
        Observable<BaseGson<ExpressGson>> queryExpress();
    }

    interface View extends BaseView {
        void loadExpress(List<ExpressGson> list);
    }

    interface Presenter {
        void queryExpress();
    }
}
