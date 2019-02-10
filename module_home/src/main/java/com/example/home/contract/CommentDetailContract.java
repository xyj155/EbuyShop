package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.CommentDetailGson;
import com.example.commonlib.gson.EmptyGson;

import rx.Observable;

public interface CommentDetailContract {
    interface Model {
        Observable<BaseGson<CommentDetailGson>> queryGoodsCommentById(String  commentId);
        Observable<BaseGson<EmptyGson>> submitGoodsComment(String userId, String content, String postId);
    }

    interface View extends BaseView {
        void setGoodsComment(CommentDetailGson goodsComment);
        void submitGoodsComment(boolean isSubmit);
    }

    interface Presenter {
        void queryGoodsCommentById(String  commentId);
        void submitGoodsComment(String userId, String content, String postId);
    }
}
