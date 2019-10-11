package com.example.commonlib.util;

import android.app.Activity;


import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.widget.Toast;

public class WebViewDownLoadListener implements DownloadListener {
    private Activity mActivity;

    public WebViewDownLoadListener(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
        new DownloadTask(mActivity).execute(url, fileName);
        System.out.println("zuoxiangyu  url == " + url);
    }

}