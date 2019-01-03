package com.example.commonlib.util;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class ThirdLoginUtil {

    private static final String TAG = "ThirdLoginUtil";
    public static void thirdLogin(Activity context, SHARE_MEDIA platform) {
        UMShareAPI  mShareAPI = UMShareAPI.get(context);
        boolean install = mShareAPI.isInstall(context, platform);
        if (!install){
            Toast.makeText(context, "你未安装客户端", Toast.LENGTH_SHORT).show();
        }else {
            mShareAPI.getPlatformInfo(context, platform, new UMAuthListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {
                    Log.i(TAG, "onStart: ");
                }

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    Log.i(TAG, "onComplete: "+map);
                }
                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                    Log.i(TAG, "onError: "+throwable.getMessage());

                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {
                    Log.i(TAG, "onCancel: ");
                }
            });
        }

    }
}
