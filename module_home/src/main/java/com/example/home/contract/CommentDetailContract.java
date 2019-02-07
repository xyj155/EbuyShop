package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.CommentDetailGson;

import rx.Observable;

public interface CommentDetailContract {
    interface Model {
        Observable<BaseGson<CommentDetailGson>> queryGoodsCommentById(String  commentId);
    }

    interface View extends BaseView {
        void setGoodsComment(CommentDetailGson goodsComment);
    }

    interface Presenter {
        void queryGoodsCommentById(String  commentId);
    }
}
