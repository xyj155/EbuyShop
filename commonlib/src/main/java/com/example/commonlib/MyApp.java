package com.example.commonlib;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.jpush.android.api.JPushInterface;

public class MyApp extends Application {
    /**
     * 上下文
     */
    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static Context getInstance() {
        return instance;
    }

}
