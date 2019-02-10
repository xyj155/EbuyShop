package com.example.home.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.CommentDetailGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.BaseObserver;
import com.example.home.contract.CommentDetailContract;
import com.example.home.model.CommentDetailModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CommentDetailPresenter extends BasePresenter<CommentDetailContract.View> implements CommentDetailContract.Presenter {

    public CommentDetailPresenter(CommentDetailContract.View mMvpView) {
        super(mMvpView);
    }

    private CommentDetailModel commentDetailModel = new CommentDetailModel();

    @Override
    public void queryGoodsCommentById(String commentId) {
        mMvpView.showDialog("");
        commentDetailModel.queryGoodsCommentById(commentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<CommentDetailGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<CommentDetailGson> commentDetailGsonBaseGson) {
                        mMvpView.hideDialog();
                        mMvpView.setGoodsComment(commentDetailGsonBaseGson.getSingleData());
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    @Override
    public void submitGoodsComment(String userId, String content, String postId) {
        commentDetailModel.submitGoodsComment(userId, content, postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        mMvpView.submitGoodsComment(emptyGsonBaseGson.isStatus());
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

    }
}
