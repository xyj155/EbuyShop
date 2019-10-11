package com.example.commonlib.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.example.commonlib.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, Void> {
    private Activity mActivity;
    public DownloadTask(Activity activity){
        mActivity = activity;
    }

    File sdFile;
    @Override
    protected Void doInBackground(String... params) {
        OutputStream out = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(15000);
            InputStream in = urlConnection.getInputStream();

            File downFile;
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_CHECKING)) {
                downFile = Environment.getExternalStorageDirectory();
                System.out.println("zuoxiangyu sdFile == 存在存储卡");
            }else{
                System.out.println("zuoxiangyu sdFile == 存储卡不存在");
                downFile = new File("/storage/emulated/0");
            }

//            sdFile = new File(getSDPath(),params[1]);//getSDPath
            sdFile = new File(downFile,params[1]);//fileName

            System.out.println("zuoxiangyu sdFile == "+sdFile.toString());

            out = new FileOutputStream(sdFile);
            System.out.println("zuoxiangyu out == "+out.toString());
            byte[] buffer = new byte[8*1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("zuoxiangyu e1 == "+e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    System.out.println("zuoxiangyu e2 == "+e.toString());
                }
            }
        }
        return null;
    }

    private String savePath = "/58apk/";
    /**
     * 获取sd
     * @return
     */
    private String getSDPath(){
        String dirPath;
        if (mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES) != null) {
            dirPath = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    + savePath;
        } else {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                dirPath = "/sdcard"+ savePath;
            } else {
                dirPath = Environment.getRootDirectory() + savePath;
            }
        }
        return dirPath;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("zuoxiangyu 开始下载");
//        showProgressDialog(); 开始转圈
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //        dismissProgressDialog(); 结束转圈
        System.out.println("zuoxiangyu完成下载");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mActivity,   "com.xuyijie.ebuyshop.fileProvider", sdFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(sdFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mActivity.startActivity(intent);
    }
}