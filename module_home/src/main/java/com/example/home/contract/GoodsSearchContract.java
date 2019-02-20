package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.GoodsGson;

import java.util.List;

import rx.Observable;

public interface GoodsSearchContract {
    interface Model {
        Observable<BaseGson<GoodsGson>> querySearchByLikeWord(String userId);
    }

    interface View extends BaseView {
        void querySearchByLikeWord(List<GoodsGson> list);
    }

    interface Presenter {
        void querySearchByLikeWord(String userId);
    }
}
