package com.example.module_message.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.MessageExpressTraceGson;
import com.example.commonlib.gson.SystemMessageGson;
import com.example.commonlib.http.BaseObserver;
import com.example.module_message.contract.MessageContract;
import com.example.module_message.model.MessageModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MessagePresenter extends BasePresenter<MessageContract.View> implements MessageContract.Presenter {
    public MessagePresenter(MessageContract.View mMvpView) {
        super(mMvpView);
    }

    private MessageModel messageModel = new MessageModel();

    @Override
    public void querySystemPushMessage(String page) {
        mMvpView.showDialog("");
        messageModel.querySystemPushMessage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SystemMessageGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SystemMessageGson> systemMessageGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (systemMessageGsonBaseGson.isStatus()) {
                            mMvpView.querySystemPushMessage(systemMessageGsonBaseGson.getData());
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }

    @Override
    public void queryUserGoodsTrace(String userId, String page) {
        mMvpView.showDialog("");
        messageModel.queryUserGoodsTrace(userId,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<MessageExpressTraceGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<MessageExpressTraceGson> systemMessageGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (systemMessageGsonBaseGson.isStatus()) {
                            mMvpView.queryUserGoodsTrace(systemMessageGsonBaseGson.getData());
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }


}
