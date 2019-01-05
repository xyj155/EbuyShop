package com.example.commonlib.commonactivity;


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.R;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.presenter.HomePresent;
import com.example.commonlib.util.RouterUtil;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

@Route(path = RouterUtil.BROWSER)
public class BrowserActivity extends BaseActivity<HomeContract.View, HomePresent> {
    private WebView mWebView;


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public HomePresent getPresenter() {
        return null;
    }


    @Override
    public int intiLayout() {
        return R.layout.activity_browser;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        mWebView = findViewById(R.id.forum_context);
        mWebView.loadUrl("https://x5.tencent.com/tbs/guide/sdkInit.html");

        mWebView.getSettings().setJavaScriptEnabled(true);// 支持js
        mWebView.setWebViewClient(new WebViewClient());//防止加载网页时调起系统浏览器
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    initToolBar().setToolBarTitle(title);
                    Log.i(TAG, "initView: "+title);
                }
            }
        });

    }

    @Override
    public void initData() {

    }
}
