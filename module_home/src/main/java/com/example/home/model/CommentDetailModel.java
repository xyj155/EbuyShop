package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.CommentDetailGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.CommentDetailContract;

import rx.Observable;

public class CommentDetailModel implements CommentDetailContract.Model {
    @Override
    public Observable<BaseGson<CommentDetailGson>> queryGoodsCommentById(String commentId) {
        return RetrofitUtils.getInstance().create().queryGoodsCommentById(commentId);
    }

    @Override
    public Observable<BaseGson<EmptyGson>> submitGoodsComment(String userId, String content, String postId) {
        return RetrofitUtils.getInstance().create().submitGoodsComment(userId,content,postId);
    }
}
