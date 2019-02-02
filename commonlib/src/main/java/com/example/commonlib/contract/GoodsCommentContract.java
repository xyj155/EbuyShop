package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.GoodsCommentGson;

import java.util.List;

import rx.Observable;

public interface GoodsCommentContract {
    interface Model {
        Observable<BaseGson<GoodsCommentGson>> queryGoodsComment(String goodsId);
    }

    interface View extends BaseView {
        void setGoodsComment(List<GoodsCommentGson> list);
    }

    interface Presenter {
        void queryGoodsComment(String goodsId);
    }
}
