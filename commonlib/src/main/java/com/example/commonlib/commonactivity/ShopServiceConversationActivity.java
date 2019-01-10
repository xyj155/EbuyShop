package com.example.commonlib.commonactivity;


import android.util.Log;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.R;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.presenter.GoodsDetailPresenter;
import com.example.commonlib.util.RouterUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;


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
    }

    @Override
    public void initData() {

    }

    public void doLogin() {
        LoginInfo info = new LoginInfo("123456", "d384036420417797c440a282cef6ff77"); // config...
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
                        Log.i(TAG, "onException: "+exception.getMessage());
                    }

                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }
}
