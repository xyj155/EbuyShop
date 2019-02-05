package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.SecondKindGson;

import java.util.List;

import rx.Observable;

public interface SecondHandsKindContract {
    interface Model {
        Observable<BaseGson<SecondKindGson>> queryAllSecondKind();
    }

    interface View extends BaseView {
        void setSecondKind(List<SecondKindGson> list);
    }

    interface Presenter {
        void queryAllSecondKind();
    }
}
