package com.example.user.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.ExpressTraceGson;

import java.util.List;

import rx.Observable;

public interface ExpressTraceContract {
    interface Model {
        Observable<BaseGson<ExpressTraceGson>> queryExpressByNum(String  expressNum);
    }

    interface View extends BaseView {
        void loadTrace(List<ExpressTraceGson> expressTraceGsons);
    }

    interface Presenter {
        void queryExpressByNum(String  expressNum);
    }
}
