package com.example.commonlib.commonactivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.MyApp;
import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.presenter.LoginPresent;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.view.MyDialog;
import com.example.commonlib.view.toast.ToastUtils;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
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

        WebViewClient webViewClient = new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = mWebView.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    initToolBar().setToolBarTitle(title);
                    Log.i(TAG, "initView: " + title);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 如下方案可在非微信内部WebView的H5页面中调出微信支付
                if (url.startsWith("weixin://wap/pay?") || url.startsWith("alipays://")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else if (url.contains("/index/emptyPage")) {
                    finish();
                }
                return false;
            }
        };
        mWebView.setWebViewClient(webViewClient);
        mWebView.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mhideDialog();
                } else {
                    createDialog("");
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        mWebView.addJavascriptInterface(new WebInterface(BrowserActivity.this), "test");
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                Uri uri = Uri.parse(url);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(intent);
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

    public class WebInterface {
        private Context context;

        public WebInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void startGoods(String goodsId) {
            Log.i(TAG, "startGoods: " + goodsId);
            Intent it = new Intent(BrowserActivity.this, GoodsDetailActivity.class);
            it.putExtra("goodsId", goodsId);
            startActivity(it);

        }

        @JavascriptInterface
        public void resetPass(String msg, String code) {
            if (code.equals("0")) {
                ToastUtils.show(msg);
                finish();
            } else {
                ToastUtils.show(msg);
            }
        }

        @JavascriptInterface
        public void confirmTel(String msg) {
            ToastUtils.show(msg);
        }

        @JavascriptInterface
        public void getService(final String phone) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showMsgDialog("拨打客服", "是否拨打人工客服", new OnItemClickListener() {
                        @Override
                        public void onConfirm(MyDialog dialog) {
                            callPhone(phone);
                        }
                    });
                }
            });

        }
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
