package com.example.commonlib.commonactivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
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

    private TextView tvTitle;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        ButterKnife.bind(this);
        mWebView = findViewById(R.id.forum_context);
        mWebView.loadUrl(getIntent().getStringExtra("url"));
        ivClose = findViewById(R.id.iv_close);
        tvTitle = findViewById(R.id.tv_title);
        mWebView.getSettings().setJavaScriptEnabled(true);// 支持js

        WebViewClient webViewClient = new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = mWebView.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setText(title);
                    ivClose.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View v) {
                            overridePendingTransition(R.anim.anim_popup_zoom_enter, R.anim.anim_popup_zoom_exit);
                            int code = getIntent().getIntExtra("code", 0);
                            if (code==88){
                                ARouter.getInstance().build(RouterUtil.Splash).navigation(BrowserActivity.this,88);
                            }
                            finish();
                        }
                    });
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
