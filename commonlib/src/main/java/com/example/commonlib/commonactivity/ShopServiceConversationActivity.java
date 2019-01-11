package com.example.commonlib.commonactivity;


import android.util.Log;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.R;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.presenter.GoodsDetailPresenter;
import com.example.commonlib.util.RouterUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;


@Route(path = RouterUtil.SHOPSERVICE)
public class ShopServiceConversationActivity extends BaseActivity<GoodsDetailContract.View, GoodsDetailPresenter> {


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public GoodsDetailPresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_shop_service;
    }

    @Override
    public void initView() {
        doLogin();
        IMMessage customMessage = MessageBuilder.createTextMessage("123456", SessionTypeEnum.P2P, "123");
        NIMClient.getService(MsgService.class).sendMessage(customMessage, false);
    }

    @Override
    public void initData() {
        Observer<List<IMMessage>> incomingMessageObserver =
                new Observer<List<IMMessage>>() {
                    @Override
                    public void onEvent(List<IMMessage> messages) {
                        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                        Log.i(TAG, "onEvent: "+messages.get(0).getContent());
                    }
                };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
    }

    public void doLogin() {
        LoginInfo info = new LoginInfo("456789", "3ec117139fb74686bae3a0bbd211b481"); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        Log.i(TAG, "onSuccess: ");
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.i(TAG, "onFailed: ");
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Log.i(TAG, "onException: " + exception.getMessage());
                    }

                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }
}
