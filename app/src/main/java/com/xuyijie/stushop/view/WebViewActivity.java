package com.xuyijie.stushop.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import com.example.commonlib.R2;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.commonactivity.GoodsDetailActivity;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.presenter.LoginPresent;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.view.MyDialog;
import com.example.commonlib.view.toast.ToastUtils;

import com.xuyijie.stushop.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity<HomeContract.View, LoginPresent> {
    @BindView(R2.id.iv_close)
    ImageView ivClose;
    private WebView mWebView;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return true;
    }

    @Override
    public LoginPresent getPresenter() {
        return null;
    }


    @Override
    public int intiLayout() {
        return com.example.commonlib.R.layout.activity_browser;
    }

    private TextView tvTitle;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(WebViewActivity.this);
        progressDialog.setMessage("正在下载...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(true);
        mWebView = findViewById(com.example.commonlib.R.id.forum_context);
        mWebView.loadUrl(getIntent().getStringExtra("url"));
        ivClose = findViewById(com.example.commonlib.R.id.iv_close);
        tvTitle = findViewById(com.example.commonlib.R.id.tv_title);
        mWebView.getSettings().setJavaScriptEnabled(true);// 支持js

        final WebViewClient webViewClient = new WebViewClient() {

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
                            overridePendingTransition(com.example.commonlib.R.anim.anim_popup_zoom_enter, com.example.commonlib.R.anim.anim_popup_zoom_exit);
                            int code = getIntent().getIntExtra("code", 0);
                            if (code == 88) {
                                ARouter.getInstance().build(RouterUtil.Splash).navigation(WebViewActivity.this, 88);
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
        mWebView.addJavascriptInterface(new WebInterface(WebViewActivity.this), "test");
//        mWebView.setDownloadListener(new WebViewDownLoadListener(this));


        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
                mWebView.setVisibility(View.GONE);
                findViewById(R.id.tb_common).setVisibility(View.GONE);
                final WebViewActivity.DownloadTask downloadTask = new WebViewActivity.DownloadTask(WebViewActivity.this);
                //execute 执行一个异步任务，通过这个方法触发异步任务的执行。这个方法要在主线程调用。
                downloadTask.execute(url);
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        downloadTask.cancel(true);
                    }
                });
            }
        });
    }

    private ProgressDialog progressDialog;


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
        if (id == com.example.commonlib.R.id.iv_close) {
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
            Intent it = new Intent(WebViewActivity.this, GoodsDetailActivity.class);
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

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        //onPreExecute(),在execute(Params... params)方法被调用后立即执行，执行在ui线程，
        // 一般用来在执行后台任务前会UI做一些标记
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            progressDialog.show();
        }

        // doInBackground这个方法在onPreExecute()完成后立即执行，
        // 用于执行较为耗时的操作，
        // 此方法接受输入参数
        // 和返回计算结果（返回的计算结果将作为参数在任务完成是传递到onPostExecute(Result result)中），
        // 在执行过程中可以调用publishProgress(Progress... values)来更新进度信息
        //后台任务的代码块
        @Override
        protected String doInBackground(String... url) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL urll = new URL(url[0]);
                Log.d("upgrade", "url1:" + urll + "////url:" + url);
                connection = (HttpURLConnection) urll.openConnection();
                connection.connect();
                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }
                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();
                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream("/sdcard/new.apk");
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        //在调用这个方法后，执行onProgressUpdate(Progress... values)，
                        //运行在主线程，用来更新pregressbar
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        //onProgressUpdate(Progress... values),
        // 执行在UI线程，在调用publishProgress(Progress... values)时，此方法被执行。
        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(progress[0]);
        }

        //onPostExecute(Result result),
        // 执行在UI线程，当后台操作结束时，此方法将会被调用。
        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            progressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
            }
//这里主要是做下载后自动安装的处理
            File file = new File("/sdcard/new.apk");
//            Intent installIntent = new Intent(Intent.ACTION_VIEW);
//            installIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(installIntent);
            installApk(WebViewActivity.this,file);
        }

    }

    public static void installApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(context, "com.xuyijie.ebuyshop.fileprovider", file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

}