package com.example.commonlib.commonactivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.MyApp;
import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.presenter.LoginPresent;
import com.example.commonlib.util.RouterUtil;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterUtil.BROWSER)
public class BrowserActivity extends BaseActivity<HomeContract.View, LoginPresent> {
    @BindView(R2.id.iv_close)
    ImageView ivClose;
    private WebView mWebView;


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public LoginPresent getPresenter() {
        return null;
    }


    @Override
    public int intiLayout() {
        return R.layout.activity_browser;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        ButterKnife.bind(this);
        mWebView = findViewById(R.id.forum_context);
        mWebView.loadUrl(getIntent().getStringExtra("url"));

        mWebView.getSettings().setJavaScriptEnabled(true);// 支持js
        mWebView.setWebViewClient(new WebViewClient());//防止加载网页时调起系统浏览器
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    initToolBar().setToolBarTitle(title);
                    Log.i(TAG, "initView: " + title);
                }
            }
        });
//        mWebView.setWebViewClient(new CommentWebViewClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 如下方案可在非微信内部WebView的H5页面中调出微信支付
                if (url.startsWith("weixin://wap/pay?") || url.startsWith("alipays://")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
//                view.loadUrl(url);
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.stopLoading();
        mWebView.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }


    //判断app是否安装
    private boolean isInstall(Intent intent) {
        return MyApp.getInstance().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //打开app
    private boolean openApp(String url) {
        if (TextUtils.isEmpty(url)) return false;
        try {
            if (!url.startsWith("http") || !url.startsWith("https") || !url.startsWith("ftp")) {
                Uri uri = Uri.parse(url);
                String host = uri.getHost();
                String scheme = uri.getScheme();
                //host 和 scheme 都不能为null
                if (!TextUtils.isEmpty(host) && !TextUtils.isEmpty(scheme)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (isInstall(intent)) {
                        startActivity(intent);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


    @Override
    public void initData() {

    }




    @OnClick({R2.id.iv_close})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.iv_close) {
            finish();
        }
    }
}
