package com.example.home.fragment.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.GoodsShareGson;

import java.util.List;

import rx.Observable;

public interface GoodsShareContract {
    interface Model {
        Observable<BaseGson<GoodsShareGson>> getUserShareCommentList(String type, String page);
    }

    interface View extends BaseView {
        void loadUserShareCommentList(List<GoodsShareGson> goodsShareGsonList);
    }

    interface Presenter {
        void getUserShareCommentList(String type, String page);
    }
}
