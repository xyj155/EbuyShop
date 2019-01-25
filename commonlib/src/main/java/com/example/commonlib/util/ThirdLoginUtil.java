package com.example.commonlib.util;


import com.example.commonlib.interfaces.UserLoginInterface;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;

public class ThirdLoginUtil {

    private static final String TAG = "ThirdLoginUtil";

    public static void ThirdLogin(String plat, final UserLoginInterface userLoginInterface) {
        Platform weibo = ShareSDK.getPlatform(plat);
        weibo.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
                userLoginInterface.failed();
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                PlatformDb db = arg0.getDb();
                userLoginInterface.successWithUser(db);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
                userLoginInterface.failed();
            }
        });
        weibo.showUser(null);//执行登录，登录后在回调里面获取用户资料
        weibo.removeAccount(true);
    }

}
